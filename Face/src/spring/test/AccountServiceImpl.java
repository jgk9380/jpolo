package spring.test;

import entity.Depart;
import entity.Employees;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
class AccountServiceImpl implements AccountService {
 
  @PersistenceContext
  private EntityManager em;
 
  @Override
  @Transactional
  public Depart save(Depart account) {
 
    if (account.getId() == null) {
      em.persist(account);
      return account;
    } else {
      return em.merge(account);
    }
  }
 
  @Override
  public List<Employees> findByCustomer(Depart customer) {
 
    TypedQuery query = em.createQuery("select a from Account a where a.customer = ?1", Depart.class);
    query.setParameter(1, customer);
 
    return query.getResultList();
  }
}