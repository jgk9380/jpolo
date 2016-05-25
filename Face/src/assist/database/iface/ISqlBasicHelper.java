package assist.database.iface;

import java.util.List;

public interface ISqlBasicHelper {


    public Object querySingle(String sql) throws Exception;

    public Object querySingle(String sql, Object[] params) throws Exception;

    public Object[] queryArray(String sql) throws Exception;

    public Object[] queryArray(String sql, Object[] params) throws Exception;

    public List<Object> queryListSingle(String sql) throws Exception; //

    public List<Object> queryListSingle(String sql, Object[] params) throws Exception;

    public List<Object[]> queryListArray(String sql) throws Exception;

    public List<Object[]> queryListArray(String sql, Object[] params) throws Exception;

    public int execUpdate(String sql) throws Exception;

    public int execUpdate(String sql, Object[] params) throws Exception;

    public int execUpdates(String sql, List<Object[]> params) throws Exception;

}
