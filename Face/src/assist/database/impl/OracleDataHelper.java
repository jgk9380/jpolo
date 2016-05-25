package assist.database.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import assist.database.iface.IDataHelper;
import assist.database.iface.ISqlHelper;


public class OracleDataHelper  implements IDataHelper {
    ISqlHelper sqlHelper;

    public OracleDataHelper(DataSource ds) throws SQLException {       
        sqlHelper= new OracleSqlHelper(ds);
    }

    @Override
    public ISqlHelper getSqlHelper() {        
        return sqlHelper;
    }
}
