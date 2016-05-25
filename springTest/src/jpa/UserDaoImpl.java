package jpa;


import entity.Depart;

import entity.Employees;

import polo.Employee;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;


public class UserDaoImpl implements IUserDao {
    @PersistenceContext
    EntityManager em;

    public UserDaoImpl() {
        super();
    }

}
