package test;

import assist.utils.EntityManagerFactoryProxy;

import entity.agent.City;

import javax.persistence.EntityManager;

public class TestJPA {
    public TestJPA() {
        super();
    }
    public static void main(String[] args) {
       EntityManager em = EntityManagerFactoryProxy.getEntityManagerFor11g();
       City city=em.find(City.class, "J100");
       System.out.println(city);
   }
}
