package assist.database.datatransfer;

import assist.database.DbUtils;

import com.bea.core.repackaged.springframework.dao.DataAccessException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class springTest {
    JdbcTemplate jdbcTemplate; // = new JdbcTemplate(DHManager.getDataSource("ora11g"));

    public springTest() throws Exception {
        super();
        jdbcTemplate = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
    }

    public void test()  {
        try {
            Map<String, Object> m = jdbcTemplate.queryForMap("select * from tab --where rownum=1");
            for (String s : m.keySet()) {
                System.out.println(s);
            }          
        } catch (Exception e) {
            System.out.println("1123");
            e.printStackTrace();            
        }
    }
    //2）预编译语句设值回调使用：
    public void testPreparedStatement2() {
        String insertSql = "insert into test(name) values (?)";
        int count = jdbcTemplate.update(insertSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, "name4");
            }
        });

        String deleteSql = "delete from test where name=?";
        count = jdbcTemplate.update(deleteSql, new Object[] { "name4" });

    }
    // 1）预编译语句及存储过程创建回调、自定义功能回调使用：
    public void testPpreparedStatement1() {
        int count = jdbcTemplate.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select count(*) from dual");
            }
        }, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
                pstmt.execute();
                ResultSet rs = pstmt.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });

    }
    //3）结果集处理回调：
    public void testResultSet1() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        String listSql = "select * from test";
        List<Map<Integer, String>> result = jdbcTemplate.query(listSql, new RowMapper<Map<Integer, String>>() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<Integer, String> row = new HashMap<>();
                row.put(rs.getInt("id"), rs.getString("name"));
                return row;
            }
        });
        //Assert.assertEquals(1, result.size());
        jdbcTemplate.update("delete from test where name='name5'");
    }

    //ResultSetExtractor使用回调方法extractData(ResultSet rs)提供给用户整个结果集，让用户决定如何处理该结果集。
    public void testResultSet3() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        String listSql = "select * from test";
        List result = jdbcTemplate.query(listSql, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (rs.next()) {
                    Map row = new HashMap();
                    row.put(rs.getInt("id"), rs.getString("name"));
                    result.add(row);
                }
                return result;
            }
        });
        // Assert.assertEquals(0, result.size());
        jdbcTemplate.update("delete from test where name='name5'");
    }

    public void test1() {
        //2. 查询一行数据并将该行数据转换为Map返回
        jdbcTemplate.queryForMap("select * from test where name='name5'");
        //3.查询一行任何类型的数据，最后一个参数指定返回结果类型
        jdbcTemplate.queryForObject("select count(*) from test", Integer.class);
        //4.查询一批数据，默认将每行数据转换为Map
        jdbcTemplate.queryForList("select * from test");
        //5.只查询一列数据列表，列类型是String类型，列名字是name
        jdbcTemplate.queryForList(" select name from test where name=?", new Object[] { "name5" }, String.class);
        //6.查询一批数据，返回为SqlRowSet，类似于ResultSet，但不再绑定到连接上
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from test");
    }


    public void testCallableStatementCreator1() {
        final String callFunctionSql = "{call FUNCTION_TEST(?)}";
        List<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter(Types.VARCHAR));
        params.add(new SqlReturnResultSet("result", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException,
                                                                   org.springframework.dao.DataAccessException {
                // TODO Implement this method
                return null;
            }
        }));

        Map<String, Object> outValues = jdbcTemplate.call(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                CallableStatement cstmt = conn.prepareCall(callFunctionSql);
                cstmt.setString(1, "test");
                return cstmt;
            }
        }, params);
        // Assert.assertEquals(4, outValues.get("result"));
    }


    //支持参数

    /**  上面咱们利用NamedParameterJdbcTemplate查询时，是将所有参数放入到了一个Map中，
     * 其实NamedParameterJdbcTemplate为咱们提供的参数模型不止Map。
     * 还有SqlParameterSource和BeanPropertySqlParameterSource。
     * 其中SqlParameterSource和咱们用Map一样，他只是对Map进行了封装。

而BeanPropertySqlParameterSource封装了一个JavaBean对象，通过JavaBean对象属性来决定命名参数的值。
     */
    public void namdJdbcTemplate() {
        List<String> ids = new ArrayList<String>();
        ids.add("id1");
        ids.add("id2");
        ids.add("id3");
        String sql = "SELECT * FROM TEST WHERE ID IN(:ids)";
        Map<String, List> params = new HashMap<>();
        SqlParameterSource sps;
        params.put("ids", ids);
        NamedParameterJdbcTemplate jdbcTemplate1 = null;
        jdbcTemplate1 = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Map<String, Object>> reslut = jdbcTemplate1.queryForList(sql, params);
    }

    /**
     * SimpleJdbcTemplate类也是基于JdbcTemplate类，但利用Java5+的可变参数列表和自动装箱和拆箱从而获取更简洁的代码。
     * 2）update(insertSql, 10, "name5")：采用Java5+可变参数列表从而代替new Object[]{10, "name5"}方式；
     *
        SimpleJdbcTemplate还提供如下方法用于获取JdbcTemplate和NamedParameterJdbcTemplate：
        1）获取JdbcTemplate对象方法：JdbcOperations是JdbcTemplate的接口
        JdbcOperations getJdbcOperations()
        2）获取NamedParameterJdbcTemplate对象方法：NamedParameterJdbcOperations是NamedParameterJdbcTemplate的接口
        NamedParameterJdbcOperations getNamedParameterJdbcOperations()
      SimpleJdbcTemplate主要提供两类方法：query及queryForXXX方法、update及batchUpdate方法。
     **/

    public static void main(String[] args)  {
        springTest st=null;
        try {
            st = new springTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        st.test();
        //  st.testPpreparedStatement1();

    }
}
