package assist.database;


import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;


public class DSManager {

    private Map<String, DataSource> dataSourceMap ;

    public DSManager() {
        dataSourceMap=new HashMap<>();
        try {
            DataSource dssDs = getJNDIDS("weblogic.jndi.WLInitialContextFactory", "T3://10.35.32.53:7001", "bastest");
            dataSourceMap.put("dss", dssDs);
            DataSource Ora11gDs = this.getDefaultDataSource();
            dataSourceMap.put("ora11g", Ora11gDs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public DataSource getDataSource(String name) throws Exception {
        return dataSourceMap.get(name);
    }

  
    public DataSource getDefaultDataSource() throws IOException, Exception {
        javax.sql.DataSource ds = null;
        InputStream is = this.getClass().getResourceAsStream("dbcp.properties");
        Properties prop = new Properties();
        prop.load(is);
        ds = BasicDataSourceFactory.createDataSource(prop);
      BasicDataSource bd;
        return ds;
    }


    private DataSource getJDBCDS(String driveClass, String url, String username, String pwd) {
        DataSource ds = null;
        try {
            Properties prop = new Properties();
            prop.put("url", url);
            prop.put("driverClassName", driveClass);
            prop.put("username", username);
            prop.put("password", pwd);
            ds = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
   
    private DataSource getJNDIDS(String factoryClass, String url, String jndiName) {
        Context ctx = null;
        DataSource ds = null;
        //System.out.println("ds0=" + ds);
        Hashtable<String, String> env = new Hashtable<>();
        if (factoryClass != null)
            env.put(Context.INITIAL_CONTEXT_FACTORY, factoryClass);
        if (url != null)
            env.put(Context.PROVIDER_URL, url);
        try {
            ctx = new InitialContext(env);
            ds = (DataSource) ctx.lookup(jndiName); //在上下文中查找数据源
            //System.out.println("ds1=" + ds);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
    }


    public static void main(String[] args) throws Exception {
        DSManager ds = new DSManager();       
        DataSource d = ds.getDataSource("dss");
        System.out.println(d);
         d = ds.getDataSource("ora11g");
        System.out.println(d);
    }

}

