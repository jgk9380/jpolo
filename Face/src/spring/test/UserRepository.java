package spring.test;

import entity.Depart;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Depart, BigDecimal>{
//�յģ�����ʲô������д
}
