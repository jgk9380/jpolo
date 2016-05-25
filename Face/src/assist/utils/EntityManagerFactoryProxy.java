package assist.utils;


import assist.EntityManagerFactoryProxyFromSpring;

import entity.Depart;

import entity.report.Report;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import jpolo.iface.meta.IPMeta;
import jpolo.iface.polo.IPolo;

import jpolo.impl.meta.PoloMeta;
import jpolo.impl.polo.Polo;
import jpolo.impl.view.PoloView;

import org.eclipse.persistence.internal.jpa.metamodel.SingularAttributeImpl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntityManagerFactoryProxy {
    private static Map<Class, IPMeta<?>> ipMap = new HashMap<>();

    //一个持久单元对应一个EntityManagerFactory
    private static final EntityManagerFactory entityManagerFactoryOra11g;

    static {
        entityManagerFactoryOra11g = Persistence.createEntityManagerFactory("entity");        
        System.out.println("pu=" + entityManagerFactoryOra11g.getPersistenceUnitUtil());
    }

    public static EntityManager getEntityManagerFor11g() {
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//        EntityManagerFactoryProxyFromSpring  emfp = (EntityManagerFactoryProxyFromSpring) ctx.getBean("emfp");
//        return emfp.getEm();
        return entityManagerFactoryOra11g.createEntityManager();
    }


    public static <P> IPMeta<P> getPoloMeta(Class<P> clazz) {
        if (!ipMap.containsKey(clazz)) {
            PoloMeta pm = new PoloMeta<>(entityManagerFactoryOra11g, clazz);
            ipMap.put(clazz, pm);
        }
        IPMeta<P> res = (IPMeta<P>) ipMap.get(clazz);
        return res;
    }

    /**
     * 修改用户id=6的数据
     */
    public static void test() throws Exception {
        final EntityManager em = entityManagerFactoryOra11g.createEntityManager();
        Depart info = em.find(Depart.class, new BigDecimal(300028406));
        IPolo ip = new Polo<>(em, info);
        ip.show();
    }

    public static <T> void testEntityView(Class<?> clazz) throws NoSuchFieldException, Exception {
        final EntityManager em = entityManagerFactoryOra11g.createEntityManager();
        PoloView<?> view = new PoloView<>(em, clazz);
        view.show(3);
    }

    public static void showMeta() {
        for (EntityType et : entityManagerFactoryOra11g.getMetamodel().getEntities()) {
            System.out.println(et.getJavaType().getSimpleName());
        }
        System.out.println("et=" + entityManagerFactoryOra11g.getMetamodel().entity(Depart.class).getBindableType());
        System.out.println("attrs=" + entityManagerFactoryOra11g.getMetamodel().entity(Depart.class).getAttributes());
        for (Attribute a : entityManagerFactoryOra11g.getMetamodel().entity(Depart.class).getAttributes()) {
            //            System.out.println("name=" + a.getName());
            //            System.out.println("javaMember.isSynthetic="+a.getJavaMember().isSynthetic());
            //            System.out.println("javaType=" + a.getJavaType());
            //            System.out.println("getPersistentAttributeType=" + a.getPersistentAttributeType());
            //            System.out.println("isAssociation=" + a.isAssociation());
            //            System.out.println("getDeclaringType=" + a.getDeclaringType());
            //            System.out.println();
            //            if (a instanceof ListAttributeImpl) {
            //                ListAttributeImpl la = (ListAttributeImpl) a;
            //                System.out.println(la.getMapping());
            //            }
            if (a instanceof SingularAttributeImpl) {
                SingularAttributeImpl sai = (SingularAttributeImpl) a;
                System.out.println("name=" + sai.getName().toString());
                System.out.println("type=" + sai.getJavaType().toString());
                System.out.println("bindtype=" + sai.getBindableType().toString());
                System.out.println("getBindableJavaType=" + sai.getBindableJavaType().toString());
                System.out.println("getMapping.getSelectFields=" + sai.getMapping().getSelectFields().toString());
                System.out.println();
            }

        }
    }


    public static void main(String[] args) throws Exception {
        //        System.out.println(emf.getClass());
        //        org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl efi = (EntityManagerFactoryImpl) emf;
        //        efi.getDatabaseSession();
        // for(EntityType et:  emf.getMetamodel().getEntities()) {
       // testEntityView(Report.class);
        Report re=EntityManagerFactoryProxy.getEntityManagerFor11g().find(Report.class, new BigDecimal(16531));
        System.out.println("re="+re);
        // }
    }
}
