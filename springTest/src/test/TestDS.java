package test;

import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.ClassPathXmlApplicationContext;

import polo.Employee;

import java.util.List;

import java.util.Map;

import javax.naming.NamingException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class TestDS {
    public TestDS() {
        super();
    }

    public static void main(String[] args) throws NamingException {
        testJndiDS();
    }

    private static void testJndiDS() throws NamingException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        JndiObjectFactoryBean job = (JndiObjectFactoryBean) ctx.getBean("jndiDataSource");
        job.afterPropertiesSet();
        DataSource ds = (DataSource) job.getObject();
        System.out.println(ds);
        JdbcTemplate jt = new JdbcTemplate(ds);
        List<Map<String, Object>> lm = jt.queryForList("select * from tab");
        for (Map<String, Object> m : lm) {
            System.out.println("tname=" + m.get("TNAME"));
        }
    }
    private static void testDS() throws NamingException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        DataSource ds = (DataSource) ctx.getBean("dataSource");       
        JdbcTemplate jt = new JdbcTemplate(ds);
        List<Map<String, Object>> lm = jt.queryForList("select * from tab");
        for (Map<String, Object> m : lm) {
            System.out.println("tname=" + m.get("TNAME"));
        }
    }
}
