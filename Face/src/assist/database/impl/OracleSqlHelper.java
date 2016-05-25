package assist.database.impl;

import java.sql.SQLException;

import java.util.List;

import javax.sql.DataSource;

import assist.utils.DataUtils;
import assist.utils.DebugUtils1;

import assist.database.iface.ISqlHelper;


public class OracleSqlHelper extends OracleBasicSqlHelper implements ISqlHelper {
    //没有实现事务管理

    public OracleSqlHelper(DataSource ds) throws SQLException {
        super(ds);
    }

    @Override
    public Object getTValue(String tableName, String col, String[] wcols, Object[] pk) throws Exception {
        String sql = getPTableColValueSql(tableName, col, wcols, pk);
        if (pk == null) {
            Object o = super.querySingle(sql, null);
            return o;
        }
        Object o = super.querySingle(sql, pk);
        return o;
    }


    @Override
    public List<Object> getTLValue(String tableName, String col, String[] wcols, Object[] pk) throws Exception {
        String sql = getPTableColValueSql(tableName, col, wcols, pk);
        List<Object> oTemp = super.queryListSingle(sql, pk);

        return oTemp;
    }

    @Override
    public Object[] getTValues(String tableName, String[] cols, String[] wcols, Object[] pk) throws Exception {
        String sql = getPTableColsValueSql(tableName, cols, wcols, pk);

        Object[] o = super.queryArray(sql, pk);

        return o;
    }

    @Override
    public List<Object[]> getTLValues(String tableName, String[] valueColMeta, String[] whereCols, Object[] pk) throws Exception {
        String sql = getPTableColsValueSql(tableName, valueColMeta, whereCols, pk);
        if (pk == null) {
            List<Object[]> o = super.queryListArray(sql, null);
            return o;
        }
        List<Object[]> o = super.queryListArray(sql, pk);
        return o;
    }


    @Override
    public int delete(String tName, String[] whereColMeta, Object[] pk) throws Exception {
        String deleteSql = "delete from " + tName + getPWhereSql(whereColMeta, pk);
        //System.out.println("deleteSql="+deleteSql);
        int i = super.execUpdate(deleteSql, pk);
        return i;
      
    }

    @Override
    public int deletes(String tName, String[] whereCols, List<Object[]> whereValue) throws Exception {
        String deleteSql = "delete from " + tName + getPWhereSql(whereCols, whereValue.get(0));
      
        int i = super.execUpdates(deleteSql, whereValue);
        return i;
    }

    @Override
    public int insert(String tName, String[] cols, Object[] values) throws Exception {
        String sqlbefore = " insert into " + tName + "(";
        String sqlAfter = " values(";
        for (int i = 0; i < cols.length; i++) {
            sqlbefore = sqlbefore + cols[i] + ",";
            sqlAfter = sqlAfter + "?,";
        }
        sqlbefore = sqlbefore.substring(0, sqlbefore.length() - 1) + ") ";
        sqlAfter = sqlAfter.substring(0, sqlAfter.length() - 1) + ")";
        String sql = sqlbefore + sqlAfter;
        //System.out.println("insert into sql="+sql);
        int i = execUpdate(sql, values);
        return i;
        
    }

    @Override
    public int inserts(String tName, String[] colNames, List<Object[]> values) throws Exception {
        String sqlbefore = " insert into " + tName + "(";
        String sqlAfter = " values(";
        for (int i = 0; i < colNames.length; i++) {
            sqlbefore = sqlbefore + colNames[i] + ",";
            sqlAfter = sqlAfter + "?,";
        }
        sqlbefore = sqlbefore.substring(0, sqlbefore.length() - 1) + ") ";
        sqlAfter = sqlAfter.substring(0, sqlAfter.length() - 1) + ")";
        String sql = sqlbefore + sqlAfter;
        int i = execUpdates(sql, values);
        return i;
    }


    @Override
    public int update(String tName, String[] cols, Object[] values, String[] whereColMeta, Object[] pk) throws Exception {
        String sql = "update  " + tName + " set ";
        for (int i = 0; i < cols.length; i++) {
            sql = sql + cols[i] + "=?,";
        }
        sql = sql.substring(0, sql.length() - 1) + "";
        sql = sql + "  " + getPWhereSql(whereColMeta, pk);
        //        System.out.println("update sql="+sql);
        //        for(Object o:Utils.jionArray(values, pk.getPKValues())){
        //            System.out.print(" "+o+" ");
        //        }
        //        System.out.println();


        int i = execUpdate(sql, DataUtils.concatObjectArray(values, pk));
        return i;
    }

    @Override
    public int updates(String tName, String[] colNames, List<Object[]> values, String[] wcols, List<Object[]> wvs) throws Exception {
        int res = 0;
        if (values.size() != wvs.size())
            return -1;
        for (int i = 0; i < values.size(); i++) {
            res = res + update(tName, colNames, values.get(i), wcols, wvs.get(i));
        }
        return res;
    }

    private String getPTableColValueSql(String tableName, String col, String[] wcols, Object[] pk) throws Exception {
        String sql = "select " + col + " from " + tableName;
        if (wcols != null)
            sql = sql + getPWhereSql(wcols, pk);

        return sql;
    }


    private String getPTableColsValueSql(String tableName, String[] cols, String[] wcols, Object[] pk) throws Exception {
        String sql = "select ";
        for (String col : cols)
            sql = sql + " " + col + " ,";
        sql = sql.substring(0, sql.length() - 1) + " from " + tableName;
        if (wcols != null)
            sql = sql + getPWhereSql(wcols, pk);

        return sql;
    }


    private String getPWhereSql(String[] whereColMeta, Object[] pk) throws Exception {
        Object[] ipva = pk;
        if (whereColMeta.length != ipva.length) {
            for (String s : whereColMeta)
                System.out.println("s=" + s);
            DebugUtils1.error("长度不一致" + whereColMeta.length + " " + ipva.length, null);
            return null;
        }
        String res = " where ";
        for (int i = 0; i < whereColMeta.length; i++) {
            res = res + whereColMeta[0] + " = ?" + " and ";
        }
        res = res.substring(0, res.length() - 4);
        return res;
    }


    /************************
    private String getSqlEqualString(Object value) throws Exception {
        if (value == null)
            return " is null";
        if (value instanceof String)
            return " ='" + value + "'";
        if (value instanceof Date) {
            DateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String reTime = ft.format(value);
            return "=to_date('" + reTime + "','yyyy-mm-dd')";
        }
        if (value instanceof Boolean) {
            Boolean b = (Boolean) value;
            if (b)
                return "='Y'";
            else
                return "='N'";
        }
        if (value instanceof Number) {
            return "=" + value.toString();
        }
        throw new Exception("错误的数据类型：" + value.getClass() + "  值为：" + value);
    }

    private String getSqlString(Object value) throws Exception {
        if (value == null)
            return "  null";
        if (value instanceof String)
            return " '" + value + "'";
        if (value instanceof Date) {
            DateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String reTime = ft.format(value);
            return "to_date('" + reTime + "','yyyy-mm-dd')";
        }
        if (value instanceof Boolean) {
            Boolean b = (Boolean) value;
            if (b)
                return "'Y'";
            else
                return "'N'";
        }
        if (value instanceof Number) {
            return "" + value.toString();
        }

        throw new Exception("错误的数据类型：" + value.getClass() + "  值为：" + value);
    }
     **********************/


}

