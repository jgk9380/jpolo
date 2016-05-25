package hello;

import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class TestMessage {
    public TestMessage() {
        super();
    }
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void printMessage() {
        System.out.println("mesaage=" + message);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        TestMessage tm = (TestMessage) context.getBean("helloWorld");
        tm.printMessage();
        DataSource ds = (DataSource) context.getBean("dataSource");
        testDataSource(ds);
        //        ds = (DataSource) context.getBean("dssDataSource");
        //        testDataSource(ds);
    }

    private static void testDataSource(DataSource ds) {
        JdbcTemplate jt = new JdbcTemplate(ds);
        List<Map<String, Object>> l = jt.queryForList("select * from tab");
        for (Map<String, Object> m : l) {
            System.out.println("tname=" + m.get("TNAME") + " ttype=" + m.get("TABTYPE"));

        }
    }
}
