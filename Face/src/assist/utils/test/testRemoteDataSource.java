package assist.utils.test;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;

import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

public class testRemoteDataSource {
    public testRemoteDataSource() {
        super();
    }

    private static DataSource getJNDIDS(String factoryClass, String url, String jndiName) {
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

    public static void testlcemf() throws Exception {
        LocalEntityManagerFactoryBean lcemf =
            new LocalEntityManagerFactoryBean();
        
        lcemf.setPersistenceUnitName("dss");
        DataSource dssDs = getJNDIDS("weblogic.jndi.WLInitialContextFactory", "T3://10.35.32.53:7001", "bastest");  

   
        EntityManagerFactory emf=lcemf.getNativeEntityManagerFactory();
        System.out.println("emf="+emf);
        EntityManager em = emf.createEntityManager();
        List l = em.createNativeQuery("select * from tab").getResultList();
        for(Object  o:l ){
            System.out.println(o);
        }
    }
public static void main(String[] args) throws Exception {
       testlcemf();
   }
}
