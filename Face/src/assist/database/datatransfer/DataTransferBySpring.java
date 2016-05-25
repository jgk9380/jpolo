package assist.database.datatransfer;

import annotation.Enum;

import assist.database.DbUtils;
import assist.database.DSManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

public class DataTransferBySpring implements IDataTransfer {
    JdbcTemplate sourceJdbcTemplate, targetJdbcTemplate;
    String sourceSchema, tableName;
    Enum.TransType transType;

    public DataTransferBySpring(String source, String target, String schema, String table,
                                Enum.TransType tt) throws Exception {
        super();
        this.sourceJdbcTemplate = new JdbcTemplate(DbUtils.getDataSource(source));
        this.targetJdbcTemplate = new JdbcTemplate(DbUtils.getDataSource(target));
        this.sourceSchema = schema;
        this.tableName = table;
        transType = tt;
    }

    List<String> getCol() throws Exception {
        String sql =
            " select column_name,data_Type,data_length from  all_TAB_COLS\n" + " where owner='" +
            sourceSchema.toUpperCase() + "' and table_name='" + tableName.toUpperCase() + "'";
        List<String> res = sourceJdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int i) throws SQLException {
                String s = rs.getString("column_name");
                return s;
            }
        });
        return res;
    }

    Long getSourceCount() throws Exception {
        String sql = " select count(*) from " + sourceSchema + "." + tableName;
        long l = sourceJdbcTemplate.queryForObject(sql, Long.class);
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

        List<Object[]> l = sourceJdbcTemplate.query(sql, new RowMapper<Object[]>() {
            @Override
            public Object[] mapRow(ResultSet rs, int i) throws SQLException {
                Object[] oa = new Object[3];
                oa[0] = rs.getString(1);
                oa[1] = rs.getString(2);
                oa[2] = rs.getString(3);
                return oa;
            }
        });

        //List<Object[]> l = idSource.getSqlHelper().queryListArray(sql);
        String creatTableSql = "create table " + tableName + "(\n";
        System.out.println("共" + l.size() + "列");
        if (l == null || l.size() < 1)
            return;
        for (Object[] oa : l) {
            creatTableSql = creatTableSql + " " + oa[0] + " " + oa[1];
            String dataType = (String) oa[1];
           System.out.println("oa[2]="+oa[2]+" a.class="+oa[2].getClass());
           // int len = ((Number) oa[2]).intValue();
           int len = Integer.parseInt((String) oa[2]);
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
        targetJdbcTemplate.execute(creatTableSql);
    }

    boolean isTargetExist() throws Exception {
        String sl = "select count(*) from user_tables " + " where   table_Name='" + tableName.toUpperCase() + "'";
        System.out.println(sl);
        int n = targetJdbcTemplate.queryForObject(sl, Integer.class);
        if (n == 1)
            return true;
        if (n == 0)
            return false;
        return false;
    }

    boolean isSourceExist() throws Exception {
        String sl =
            "select count(*) from all_tables where owner='" + sourceSchema.toUpperCase() + "' and table_Name='" +
            tableName.toUpperCase() + "'";

        int n = sourceJdbcTemplate.queryForObject(sl, Integer.class);
        if (n == 1)
            return true;
        if (n == 0)
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


    public void preDispTable() throws Exception {
        this.createTable();
        if (transType == Enum.TransType.truncate) {
            String sql = "truncate table " + tableName;
            targetJdbcTemplate.execute(sql);
        }
        if (transType == Enum.TransType.drop) {
            String sql = "drop table " + tableName;
            targetJdbcTemplate.execute(sql);
        }
        this.createTable();
    }

    @Override
    public long transfer() throws Exception {      
        preDispTable();
        final String selectSql = this.getSelectSql();
        final String insertSql = this.getInsertSql();

        final long all = this.getSourceCount();
        long pos = 0;
        final Date startDate = new Date();
        sourceJdbcTemplate.query(selectSql, new RowCallbackHandler() {
            int currentPos = 1;
            long per;
            List<String> cols = getCol();
            List<Object[]> tempList = new ArrayList<>();
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                currentPos++;
                Object[] oa = new Object[cols.size()];
                for (int i = 0; i < cols.size(); i++) {
                    oa[i] = rs.getObject(cols.get(i));
                }
                tempList.add(oa);
                long cper = currentPos * 100 / all;
                if (currentPos % 20000 == 0 || cper > per || currentPos == all) {
                    targetJdbcTemplate.batchUpdate(insertSql,tempList );
                    tempList.clear();
                    per = cper;
                    String info =
                        "已完成：" + all + "中的" + currentPos + "条数据  完成率：" + currentPos * 100 / all + "%" + " 用时：" +
                        (new Date().getTime() - startDate.getTime()) / 1000 + "秒";
                    System.out.println(info);
                }
            }
        });


        return 0L;
    }

    public static void main(String[] args) throws Exception {
        DSManager dsm = new DSManager();
        DataTransferBySpring dtf =
            new DataTransferBySpring("dss", "ora11g", "regionj", "j_code_city", Enum.TransType.drop);
        dtf.transfer();
    }
}
