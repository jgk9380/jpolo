package test;

import assist.utils.EntityManagerFactoryProxy;

import entity.TableTransferTask;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import jpolo.iface.polo.IPolo;

import jpolo.impl.polo.Polo;

public class enumEntityTest {

    public enumEntityTest() {
        super();
    }

    public static void main(String[] args) throws Exception {
    EntityManager em = EntityManagerFactoryProxy.getEntityManagerFor11g();
//        TableTransferTask ttt = new TableTransferTask();
//        ttt.setDay(11);
//        ttt.setHour(8);
//        ttt.setCycle(TransCycle.month);
//        ttt.setTableName("zdm_real_user");
//        ttt.setTransType(TransType.truncate);
//        EntityTransaction trans = em.getTransaction();
//        trans.begin();
//        em.persist(ttt);
//        trans.commit();
      
        TableTransferTask xt =
            EntityManagerFactoryProxy.getEntityManagerFor11g().find(TableTransferTask.class, new BigDecimal(10078));
        IPolo ip=new Polo<>(em,xt);
       // ip.show();
        ip.getPoloField("cycle").getSelectItem();
    }
}
