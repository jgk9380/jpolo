package spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Client {


    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        Object o = ctx.getBean("dataSource");
        System.out.println(o);
        org.springframework.orm.jpa.LocalEntityManagerFactoryBean lcef =    (org.springframework.orm.jpa.LocalEntityManagerFactoryBean) o;
//        org.springframework.orm.jpa.LocalEntityManagerFactoryBean lcef =
//            (LocalEntityManagerFactoryBean) ctx.getBean("ora11g");
      
    }
}
