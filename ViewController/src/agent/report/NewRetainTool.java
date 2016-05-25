package agent.report;

import assist.database.DbUtils;

import entity.agent.Channel;
import entity.agent.City;
import entity.agent.Grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 存量保有
 */
public class NewRetainTool {
    JdbcTemplate jt;
    String sqlBody =
        "sum(u) u,sum(f02) f,\n" +
        "sum(u01) u01,sum(u02) u02,sum(u03) u03,sum(u04) u04,sum(u05) u05,sum(u06) u06,sum(u07) u07,sum(u08) u08,sum(u09) u09,sum(u10) u10,sum(u11) u11,sum(u12) u12,\n" +
        "sum(f01) f01,sum(f02) f02,sum(f03) f03,sum(f04) f04,sum(f05) f05,sum(f06) f06,sum(f07) f07,sum(f08) f08,sum(f09) f09,sum(f10) f10,sum(f11) f11,sum(f12) f12 \n" +
        "from j_NEW_retain_mv a,j_code_channel b,j_code_grid c ,j_code_city d\n" +
        "where a.develop_channel=b.id(+) and b.grid_id=c.id(+) and c.city_id=d.id(+) \n";

    public NewRetainTool(JdbcTemplate jt) {
        super();
        this.jt = jt;
    }

    String getByTimeSql() {
        String result = null;
        return result;
    }

    List<Map<String, Object>> getAllSql(String month) {
        String sql =
            "select nvl(d.name,'本部') cname,\n" + sqlBody + "and acct_month=:month \n" +
            "group  by nvl(d.name,'本部')  order by nvl(d.name,'本部')";
        System.out.println("allSql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("month", month);
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }

    List<Map<String, Object>> getCityLm(City city, String month) {
        String sql =
            "select nvl(c.name,'本部') cname,\n" + sqlBody + "and acct_month=:month  and d.id=:cityID \n" +
            "group  by nvl(c.name,'本部')  ";
        System.out.println("citySql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("month", month);
        p2.put("cityID", city.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }

    List<Map<String, Object>> getGridLm(Grid grid, String month) {
        String sql =
            "select nvl(b.name,'本部') cname,\n" + sqlBody + "and acct_month=:month and c.id=:gridID \n" +
            "group  by nvl(b.name,'本部')  order by u desc  ";
        System.out.println("gridSql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("month", month);
        p2.put("gridID", grid.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }

    public List<Map<String, Object>> getSingleNewRetain(String month) {
        String sql = " ";
        for (int i = 0; i < 9; i++) {
            String tempMonth =
                jt.queryForObject("select to_char(add_months(to_date('" + month + "','YYYYMM')," + i +
                                  "),'YYYYMM') from dual", String.class);
            sql = sql + getSingleSqlForMongh(i);
            sql =
                sql + "from j_NEW_retain_mv a,j_code_channel b,j_code_grid c ,j_code_city d \n  " +
                "where a.develop_channel=b.id(+) and b.grid_id=c.id(+) and c.city_id=d.id(+) and acct_month='" +
                tempMonth + "' \n" + ")\n" + "union\n";
        }
        sql = sql.substring(0, sql.length() - 6);
        System.out.println("singleNewSql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("month", month);

        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }

    public List<Map<String, Object>> getSingleCityNewRetain(String month, City city) {
        String sql = " ";
        for (int i = 0; i < 9; i++) {
            String tempMonth =
                jt.queryForObject("select to_char(add_months(to_date('" + month + "','YYYYMM')," + i +
                                  "),'YYYYMM') from dual", String.class);
            sql = sql + getSingleSqlForMongh(i);
            sql =
                sql + "from j_NEW_retain_mv a,j_code_channel b,j_code_grid c ,j_code_city d \n  " +
                "where a.develop_channel=b.id(+) and b.grid_id=c.id(+) and c.city_id=d.id(+) and acct_month='" +
                tempMonth + "' and d.id=:cityID\n" + ")\n" + "union\n";
        }

        sql = sql.substring(0, sql.length() - 6);
        System.out.println("singleNewCitySql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("month", month);
        p2.put("cityID", city.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }

    public List<Map<String, Object>> getSingleGridNewRetain(String month, Grid grid) {
        String sql = " ";
        for (int i = 0; i < 9; i++) {
            String tempMonth =
                jt.queryForObject("select to_char(add_months(to_date('" + month + "','YYYYMM')," + i +
                                  "),'YYYYMM') from dual", String.class);
            sql = sql + getSingleSqlForMongh(i);
            sql =
                sql + "from j_NEW_retain_mv a,j_code_channel b,j_code_grid c ,j_code_city d \n  " +
                "where a.develop_channel=b.id(+) and b.grid_id=c.id(+) and c.city_id=d.id(+) and acct_month='" +
                tempMonth + "' and c.id=:gridID\n" + ")\n" + "union\n";
        }

        sql = sql.substring(0, sql.length() - 6);
        System.out.println("singleNewGridSql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("month", month);
        p2.put("gridID", grid.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }

    public List<Map<String, Object>> getSingleChannelNewRetain(String month, Channel channel) {
        String sql = " ";
        for (int i = 0; i < 9; i++) {
            String tempMonth =
                jt.queryForObject("select to_char(add_months(to_date('" + month + "','YYYYMM')," + i +
                                  "),'YYYYMM') from dual", String.class);
            sql = sql + getSingleSqlForMongh(i);
            sql =
                sql + "from j_NEW_retain_mv a,j_code_channel b,j_code_grid c ,j_code_city d \n  " +
                "where a.develop_channel=b.id(+) and b.grid_id=c.id(+) and c.city_id=d.id(+) and acct_month='" +
                tempMonth + "' and b.id=:channelID\n" + ")\n" + "union\n";
        }

        sql = sql.substring(0, sql.length() - 6);
        System.out.println("singleNewGridSql=" + sql);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("channelID", channel.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(sql, p2);
        return res;
    }


    public static void main(String[] args) throws Exception {
        JdbcTemplate jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
        NewRetainTool nrt = new NewRetainTool(jt);
        List<Map<String, Object>> res = nrt.getSingleNewRetain("201501");
        System.out.println(res);
    }

    private String getSingleSqlForMongh(int i) {
        String sql = " ";
        sql = sql + "(\n  " + "select " + (i + 1) + " month, sum(u) u, sum(f02) f,\n ";
        { //取用户数
            for (int j = 0; j < i; j++) {
                sql = sql + " 0, ";
            }
            for (int k = 1; k <= 12 - i; k++) {
                if (k < 10)
                    sql = sql + " sum(u0" + k + ") u0" + k + ",";
                else
                    sql = sql + " sum(u" + k + ") u" + k + ",";
            }
        }
        sql = sql + "\n ";
        { //取收入数
            for (int j = 0; j < i; j++) {
                sql = sql + " 0, ";
            }
            for (int k = 1; k <= 12 - i; k++) {
                if (k < 10)
                    sql = sql + " sum(f0" + k + ") f0" + k + ",";
                else
                    sql = sql + " sum(f" + k + ") f" + k + ",";
            }
        }
        sql = sql.substring(0, sql.length() - 1) + "\n  ";
        return sql;
    }
}
