package assist.database.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import assist.utils.DebugUtils1;

import assist.database.iface.ISqlBasicHelper;

import org.apache.log4j.Logger;

public class OracleBasicSqlHelper implements ISqlBasicHelper {
    DataSource ds;
    // Connection con;

    public OracleBasicSqlHelper(DataSource ds) throws SQLException {
        this.ds = ds;
        //  Connection  con = ds.getConnection();
        //con.setAutoCommit(true);
    }

    /*
    public Object executeValueQuery(String sql) throws Exception;
    public Object[] executeValuesQuery(String sql) throws Exception;
    public List<Object> executeLValueQuery(String sql) throws Exception;//
    public List<Object[]> executeLValuesQuery(String sql) throws Exception;
    public int executeUpdate(String sql) throws Exception;
*/

    @Override
    public List<Object[]> queryListArray(String sql) throws SQLException, Exception {
        Date dt1 = new Date();
        Statement stat = null;
        ResultSet rs = null;
        List<Object[]> resl = null;
        Connection con = null;
        try {
            con = ds.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            int colCount = rs.getMetaData().getColumnCount();
            resl = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] temp = new Object[colCount];
                for (int i = 0; i < colCount; i++) {
                    temp[i] = rs.getObject(i + 1);
                }
                resl.add(temp);
            }
        } catch (SQLException e) {
            String errMsg = "错误信息 messae=" + e.getMessage() + "  sql=" + sql;
            System.out.println(errMsg);
            Logger.getLogger(OracleBasicSqlHelper.class).error(errMsg);
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (stat != null)
                stat.close();
            if (con != null)
                con.close();
        }
        Date dt2 = new Date();
        if (dt2.getTime() - dt1.getTime() > 1000)
            Logger.getLogger(OracleBasicSqlHelper.class).info("花费时间" + (dt2.getTime() - dt1.getTime()) + "毫秒" +
                                                              "   sql=" + sql);

        return resl;
    }

    @Override
    public List<Object> queryListSingle(String sql) throws Exception { //一个字段
        List<Object[]> l = queryListArray(sql);
        List<Object> resl = new ArrayList<>();
        for (Object[] oa : l) {
            if (oa.length > 1) {
                throw new Exception("结果>1列  sql=" + sql);
            }
            resl.add(oa[0]);
        }
        return resl;
    }

    @Override
    public Object[] queryArray(String sql) throws Exception { //一条记录
        List<Object[]> l = queryListArray(sql);
        if (l.size() > 1) {
            throw new Exception("结果>1条记录 sql=" + sql);
        }
        if (l.size() == 0) {
            Object[] o = null;
            return o;
        }
        return l.get(0);
    }

    @Override
    public Object querySingle(String sql) throws Exception { //一条记录一个字段
        List<Object[]> l = queryListArray(sql);
        if (l.size() == 0)
            return null;
        if (l.size() > 1) {
            Logger.getLogger(OracleBasicSqlHelper.class).error("结果>1条记录 sql=" + sql);
            throw new Exception("结果>1条记录");
        }
        Object[] reslm = l.get(0);
        if (reslm.length > 1) {
            Logger.getLogger(OracleBasicSqlHelper.class).error("结果>1列  sql=" + sql);
            throw new Exception("结果>1列");
        }

        return reslm[0];
    }

    @Override
    public int execUpdate(String sql) throws SQLException {
        Statement stat = null;
        int i;
        Connection con = null;
        try {
            con = ds.getConnection();
            stat = con.createStatement();
            i = stat.executeUpdate(sql);
            stat.close();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(OracleBasicSqlHelper.class).error("错误的SQL=" + sql);
            throw e;
        } finally {
            stat.close();
            con.close();
        }
        return i;
    }

    @Override
    public List<Object[]> queryListArray(String sql, Object[] params) throws Exception {
        Date dt1 = new Date();
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Object[]> resl = null;
        Connection con = null;

        con = ds.getConnection();
        try {
            stat = con.prepareStatement(sql);
            if (params != null)
                for (int i = 0; i < params.length; i++) {
                    stat.setObject(i + 1, params[i]);
                }
            rs = stat.executeQuery();
            int colCount = rs.getMetaData().getColumnCount();
            resl = new ArrayList<Object[]>();
            while (rs.next()) {
                Object[] temp = new Object[colCount];
                for (int i = 0; i < colCount; i++) {
                    temp[i] = rs.getObject(i + 1);
                }
                resl.add(temp);
            }
        } catch (SQLException e) {
            String info = "sql=" + sql + "\n参数：(";
            for (Object o : params) {
                info = info + o + ",";
            }
            info = info.substring(0, info.length() - 1) + ")\n";
            DebugUtils1.error(info, e);

            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (stat != null)
                stat.close();
            con.close();
        }
        Date dt2 = new Date();
        if (dt2.getTime() - dt1.getTime() > 1000)
            Logger.getLogger(OracleBasicSqlHelper.class).warn("--debug  花费时间" + (dt2.getTime() - dt1.getTime()) + "毫秒" +
                                                              "   sql=" + sql);

        return resl;
    }

    @Override
    public Object querySingle(String sql, Object[] params) throws Exception {
        List<Object[]> l = queryListArray(sql, params);
        if (l.size() == 0)
            return null;
        if (l.size() > 1)
            DebugUtils1.error("结果>1条记录 sql=" + sql, null);
        Object[] reslm = l.get(0);
        if (reslm.length > 1)
            DebugUtils1.error("结果>1列  sql=" + sql, null);
        return reslm[0];
    }

    @Override
    public Object[] queryArray(String sql, Object[] params) throws Exception {
        List<Object[]> l = queryListArray(sql, params);
        if (l.size() == 0) {
            Object[] o = null;
            return o;
        }
        if (l.size() > 1) {
            throw new Exception("结果>1条记录 sql=" + sql);
        }

        return l.get(0);
    }


    @Override
    public List<Object> queryListSingle(String sql, Object[] params) throws Exception {
        List<Object[]> l = queryListArray(sql, params);
        List<Object> resl = new ArrayList<>();
        for (Object[] oa : l) {
            if (oa.length > 1) {
                throw new Exception("结果>1列  sql=" + sql);
            }
            resl.add(oa[0]);
        }
        return resl;
    }

    @Override
    public int execUpdate(String sql, Object[] params) throws Exception {
        PreparedStatement pstat = null;
        int res = 0;
        Connection con = null;

        con = ds.getConnection();
        try {
            pstat = con.prepareStatement(sql);
            for (int j = 0; j < params.length; j++)
                pstat.setObject(j + 1, params[j]);
            res = pstat.executeUpdate();
            //            {//测试
            //                String info = "sql=" + sql + "\n  参数：(";
            //                for (Object o : params) {
            //                    info = info + o + ",";
            //                }
            //                info =
            //                    info.substring(0, info.length() - 1) + ")\n";
            //                DebugUtils.info(info);
            //            }
            pstat.close();
        } catch (SQLException e) {
            String info = "sql=" + sql + "\n  参数：(";
            for (Object o : params) {
                info = info + o + ",";
            }
            info = info.substring(0, info.length() - 1) + ")\n";
            DebugUtils1.error(info, e);

            throw e;
        } finally {
            pstat.close();
            con.close();
        }
        return res;
    }

    @Override
    public int execUpdates(String sql, List<Object[]> paramsl) throws Exception {
        PreparedStatement pstat = null;
        int res;
        Connection con = null;

        con = ds.getConnection();
        try {
            pstat = con.prepareStatement(sql);
            for (int j = 0; j < paramsl.size(); j++) {
                for (int k = 0; k < paramsl.get(j).length; k++) {
                    pstat.setObject(j + 1, paramsl.get(j)[j]);
                }
                pstat.addBatch();
            }
            res = pstat.executeUpdate();
            pstat.close();
        } catch (SQLException e) {
            String info = "sql=" + sql + "\n  参数：(";
            for (Object o : paramsl) {
                info = info + "," + o;
            }
            info = info + ")\n";
            DebugUtils1.error(info, e);
            throw e;
        } finally {
            pstat.close();
            con.close();
        }
        return res;
    }


    public static void main(String[] args) {
        ;
    }


}
