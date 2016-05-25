package assist.database.iface;


import java.util.List;


public interface ISqlHelper extends ISqlBasicHelper {
    //具体数据相关的逻辑在这里
    
    Object getTValue(String tName, String vcol, String[] whereCols, Object[] whereValue) throws Exception;

    List<Object> getTLValue(String tName, String vcol, String[] whereCols, Object[] whereValue) throws Exception;

    Object[] getTValues(String tName, String[] vcols, String[] whereCols, Object[] whereValue) throws Exception;

    List<Object[]> getTLValues(String tName, String[] vcols, String[] whereCols, Object[] whereValue) throws Exception;


    int delete(String tName, String[] whereCols, Object[] whereValue) throws Exception;
    int deletes(String tName, String[] whereCols, List<Object[]> whereValue) throws Exception;

    int insert(String tName, String[] colNames, Object[] values) throws Exception;
    int inserts(String tName, String[] colNames, List<Object[]> values) throws Exception;

    int update(String tName, String[] colNames, Object[] values, String[] whereCols, Object[] whereValue) throws Exception;  
    int updates(String tName, String[] colNames, List<Object[]> values, String[] whereCols, List<Object[]> whereValue) throws Exception;

    

    

    //int updates(String tName, String[] colNames, List<Object[]> values, String[] whereCols, IPrimaryKey whereValue) throws Exception;

}
