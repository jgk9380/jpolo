package jpa;

import entity.Employees;

import polo.Employee;

import org.springframework.data.repository.Repository;

public interface IUserDao extends Repository<Employees, String> {
   
}
