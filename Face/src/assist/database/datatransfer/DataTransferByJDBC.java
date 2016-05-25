package assist.database.datatransfer;

import annotation.Enum;

import assist.database.DbUtils;
import assist.database.DSManager;
import assist.database.iface.IDataHelper;
import assist.database.impl.OracleBasicSqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class DataTransferByJDBC implements IDataTransfer {
    String source, target;
    String sourceSchema, tableName;
    Enum.TransType transType;

    public DataTransferByJDBC(String source, String target, String schema, String table, Enum.TransType tt) {
        super();
        this.source = source;
        this.target = target;
        this.sourceSchema = schema;
        this.tableName = table;
        transType = tt;
    }

    List<String> getCol() throws Exception {
        String sql =
            " select column_name,data_Type,data_length from  all_TAB_COLS\n" + " where owner='" +
            sourceSchema.toUpperCase() + "' and table_name='" + tableName.toUpperCase() + "'";
        //System.out.println(sql);
        IDataHelper idSource = DbUtils.getDataHelper(source);
        List<Object[]> l = idSource.getSqlHelper().queryListArray(sql);
        List<String> res = new ArrayList<>();
        for (Object[] oa : l) {
            res.add((String) oa[0]);
        }
        return res;
    }

    Number getSourceCount() throws Exception {
        String sql = " select count(*) from " + sourceSchema + "." + tableName;
        IDataHelper idSource = DbUtils.getDataHelper(source);
        Number l = (Number) idSource.getSqlHelper().querySingle(sql);

        return l;
    }

    public void createTable() throws Exception {
        if (!this.isSourceExist()) {
            System.out.println("源表不存在");
            return;
        }
        if (this.isTargetExist()) {
            System.out.println("目标表已存在");
            return;
        }
        //        JdbcTemplate sourceJdbcTemp = new JdbcTemplate(source);
        //        JdbcTemplate targetJdbcTemp = new JdbcTemplate(target);
        String sql =
            " select column_name,data_Type,data_length from  all_TAB_COLS\n" + " where owner='" +
            sourceSchema.toUpperCase() + "' and table_name='" + tableName.toUpperCase() + "'";
        //System.out.println(sql);
        IDataHelper idSource = DbUtils.getDataHelper(source);
        List<Object[]> l = idSource.getSqlHelper().queryListArray(sql);
        String creatTableSql = "create table " + tableName + "(\n";
        System.out.println("共" + l.size() + "列");
        if (l == null || l.size() < 1)
            return;
        for (Object[] oa : l) {
            creatTableSql = creatTableSql + " " + oa[0] + " " + oa[1];
            String dataType = (String) oa[1];
            int len = ((Number) oa[2]).intValue();
            if (dataType.equalsIgnoreCase("DATE")) {
                creatTableSql = creatTableSql + ",\n";
            } else if (dataType.equalsIgnoreCase("VARCHAR2")) {
                creatTableSql = creatTableSql + "(" + (len * 2) + "),\n";
            } else {
                creatTableSql = creatTableSql + "(" + len + "),\n";
            }
        }
        creatTableSql = creatTableSql.substring(0, creatTableSql.length() - 2) + ")";
        //System.out.println("createt=" + creatTableSql);
        IDataHelper idTarget = DbUtils.getDataHelper(target);
        idTarget.getSqlHelper().execUpdate(creatTableSql);
    }

    boolean isTargetExist() throws Exception {
        IDataHelper idSource = DbUtils.getDataHelper(target);
        String sl = "select count(*) from user_tables " + " where   table_Name='" + tableName.toUpperCase() + "'";
        System.out.println(sl);
        Number n = (Number) idSource.getSqlHelper().querySingle(sl);
        if (n.intValue() == 1)
            return true;
        if (n.intValue() == 0)
            return false;
        return false;
    }

    boolean isSourceExist() throws Exception {
        IDataHelper idSource = DbUtils.getDataHelper(source);
        String sl =
            "select count(*) from all_tables where owner='" + sourceSchema.toUpperCase() + "' and table_Name='" +
            tableName.toUpperCase() + "'";
        System.out.println(sl);
        Number n = (Number) idSource.getSqlHelper().querySingle(sl);
        if (n.intValue() == 1)
            return true;
        if (n.intValue() == 0)
            return false;
        return false;
    }

    String getSelectSql() throws Exception {
        String selectSql = "select  ";
        List<String> cols = this.getCol();
        for (String s : cols) {
            selectSql = selectSql + s + " ,";
        }
        selectSql = selectSql.substring(0, selectSql.length() - 1) + " from " + sourceSchema + "." + tableName;
        return selectSql;
    }

    String getInsertSql() throws Exception {
        List<String> cols = this.getCol();
        String insertSql = "insert into " + tableName + "(";
        for (String s : cols) {
            insertSql = insertSql + s + ",\n";
        }
        insertSql = insertSql.substring(0, insertSql.length() - 2) + ") values(";
        for (String s : cols) {
            insertSql = insertSql + "?,";
        }
        insertSql = insertSql.substring(0, insertSql.length() - 1) + " )";
        return insertSql;
    }

  

    public void truncateTable() throws Exception {
        String sql = "truncate table " + tableName;
        //System.out.println(sql);
        DbUtils.getDataHelper(target).getSqlHelper().execUpdate(sql);

    }

    @Override
    public long transfer() throws Exception {
        this.createTable();
        if (transType == Enum.TransType.truncate)
            this.truncateTable();
        String selectSql = this.getSelectSql();
        try {
            Number all = this.getSourceCount();
            //Number all=200000;
            Date startDate = new Date();
            Connection sourceCon = DbUtils.getDataSource(source).getConnection();
            Statement sourceStat = sourceCon.createStatement();
            ResultSet sourceRs = sourceStat.executeQuery(selectSql);

            Connection targetCon = DbUtils.getDataSource(target).getConnection();
            PreparedStatement targetPStat = targetCon.prepareStatement(this.getInsertSql());

            int currentPos = 0;
            int per = 0;
            List<String> cols = this.getCol();
            while (sourceRs.next()) {
                currentPos++;
                //System.out.println("cp="+currentPos);
                for (int i = 0; i < cols.size(); i++) {
                    targetPStat.setObject(i + 1, sourceRs.getObject(i + 1));
                }
                targetPStat.addBatch();

                int cper = currentPos * 100 / all.intValue();
                if (cper > per && cper % 5 == 0) {
                    targetPStat.executeBatch();
                    targetPStat.clearBatch();
                    per = cper;
                    System.out.println("已完成：" + all + "中的" + currentPos + "条数据  完成率：" +
                                       currentPos * 100 / all.intValue() + "%" + " 用时：" +
                                       (new Date().getTime() - startDate.getTime()) / 1000 + "秒");
                }

            }
            if (sourceRs != null)
                sourceRs.close();
            if (sourceStat != null)
                sourceStat.close();
            if (sourceCon != null)
                sourceCon.close();
        } catch (SQLException e) {
            String errMsg = "错误信息 messae=" + e.getMessage();
            System.out.println(errMsg);
            Logger.getLogger(OracleBasicSqlHelper.class).error(errMsg);
            e.printStackTrace();
            throw e;
        }

        return 0L;
    }

    public static void main(String[] args) throws Exception {
        DSManager dsm = new DSManager();
        DataTransferByJDBC dtf = new DataTransferByJDBC("dss", "ora11g", "regionj", "zdm_real_user", Enum.TransType.truncate);
        dtf.transfer();
    }
}
