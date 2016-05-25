package spring.test;

import entity.Depart;
import entity.Employees;

import java.util.List;

public interface AccountService {
    public Depart save(Depart account);
    public List<Employees> findByCustomer(Depart customer);
}
