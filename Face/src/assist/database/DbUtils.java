package assist.database;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import assist.database.iface.IDataHelper;
import assist.database.impl.OracleDataHelper;


public class DbUtils {
    private static DSManager dsManager = new DSManager();
//

    public DbUtils() {
        super();
        
    }

    private static DSManager getDSManager() {
        if (dsManager == null)
            dsManager = new DSManager();
        return dsManager;
    }

    public static IDataHelper getDataHelper(String name) throws Exception {
        DataSource ds = getDSManager().getDataSource(name);
        if(ds==null) throw new Exception("不存在的数据源");
        IDataHelper id = null;
        id = new OracleDataHelper(ds);
        return id;
    }
    public static DataSource getDataSource(String name) throws Exception {
        DataSource ds = getDSManager().getDataSource(name);
        if(ds==null) throw new Exception("不存在的数据源");        
        return ds;
    }

    public static void main(String[] args) throws Exception {
        IDataHelper id = getDataHelper("dss");
        Date d1 = new Date();
        List<Object> l = id.getSqlHelper().queryListSingle("select staff_id  from regionj.regionj_login");
        int i = 0;
        System.out.println("count=" + l.size());

    }
}


