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
    //2��Ԥ���������ֵ�ص�ʹ�ã�
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
    // 1��Ԥ������估�洢���̴����ص����Զ��幦�ܻص�ʹ�ã�
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
    //3�����������ص���
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

    //ResultSetExtractorʹ�ûص�����extractData(ResultSet rs)�ṩ���û���������������û�������δ���ý������
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
        //2. ��ѯһ�����ݲ�����������ת��ΪMap����
        jdbcTemplate.queryForMap("select * from test where name='name5'");
        //3.��ѯһ���κ����͵����ݣ����һ������ָ�����ؽ������
        jdbcTemplate.queryForObject("select count(*) from test", Integer.class);
        //4.��ѯһ�����ݣ�Ĭ�Ͻ�ÿ������ת��ΪMap
        jdbcTemplate.queryForList("select * from test");
        //5.ֻ��ѯһ�������б���������String���ͣ���������name
        jdbcTemplate.queryForList(" select name from test where name=?", new Object[] { "name5" }, String.class);
        //6.��ѯһ�����ݣ�����ΪSqlRowSet��������ResultSet�������ٰ󶨵�������
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


    //֧�ֲ���

    /**  ������������NamedParameterJdbcTemplate��ѯʱ���ǽ����в������뵽��һ��Map�У�
     * ��ʵNamedParameterJdbcTemplateΪ�����ṩ�Ĳ���ģ�Ͳ�ֹMap��
     * ����SqlParameterSource��BeanPropertySqlParameterSource��
     * ����SqlParameterSource��������Mapһ������ֻ�Ƕ�Map�����˷�װ��

��BeanPropertySqlParameterSource��װ��һ��JavaBean����ͨ��JavaBean������������������������ֵ��
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
     * SimpleJdbcTemplate��Ҳ�ǻ���JdbcTemplate�࣬������Java5+�Ŀɱ�����б���Զ�װ��Ͳ���Ӷ���ȡ�����Ĵ��롣
     * 2��update(insertSql, 10, "name5")������Java5+�ɱ�����б�Ӷ�����new Object[]{10, "name5"}��ʽ��
     *
        SimpleJdbcTemplate���ṩ���·������ڻ�ȡJdbcTemplate��NamedParameterJdbcTemplate��
        1����ȡJdbcTemplate���󷽷���JdbcOperations��JdbcTemplate�Ľӿ�
        JdbcOperations getJdbcOperations()
        2����ȡNamedParameterJdbcTemplate���󷽷���NamedParameterJdbcOperations��NamedParameterJdbcTemplate�Ľӿ�
        NamedParameterJdbcOperations getNamedParameterJdbcOperations()
      SimpleJdbcTemplate��Ҫ�ṩ���෽����query��queryForXXX������update��batchUpdate������
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
