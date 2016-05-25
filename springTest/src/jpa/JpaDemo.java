package jpa;

import entity.Depart;
import entity.Employees;

import java.math.BigDecimal;

import polo.Employee;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JpaDemo{   
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jpa-beans.xml");
        IUserDao userDao = (IUserDao) ctx.getBean("userDao");
        System.out.println(userDao);
        Employees emp = new Employees();
        Depart dt=new Depart();
        dt.setId(new BigDecimal  (300022553));
        emp.setId("testt");  
        emp.setDepart(dt);
        emp.setName("pppssss21");     
    }
}
