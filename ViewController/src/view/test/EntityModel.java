package view.test;

import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public class EntityModel {

    private EntityManagerFactory emf;

    public EntityModel() {
        super();
        emf = Persistence.createEntityManagerFactory("ora11g");
    }

    Set<EntityType<?>> getEntities() {
        Metamodel mm = emf.getMetamodel();
        return mm.getEntities();
    }

    public <T> void show(EntityType<T> et) {
        String res = "name=" + et.getName();
        res = res + "\nBindableJavaType=" + et.getBindableJavaType();
        res = res + "\nBindableType=" + et.getBindableType();
        res = res + "\nPersistenceType=" + et.getPersistenceType();
        res = res + "\nIdType=" + et.getIdType().getPersistenceType() + " javaType=" + et.getIdType().getJavaType();
      System.out.println(res);
        Set<Attribute<? super T, ?>> seta = et.getAttributes();
        for (Attribute<?, ?> a : seta) {
            show(a);
            System.out.println();
        }

    }

    public void show(Attribute<?, ?> a) {
        String res = "      name=" + a.getName();
        res = res + "\n     getDeclaringType=" + a.getDeclaringType();
        res = res + "\n     getJavaMember=" + a.getJavaMember();
        res = res + "\n     getJavaType=" + a.getJavaType();
        res = res + "\n     getPersistentAttributeType=" + a.getPersistentAttributeType();
        System.out.println(res);
    }

    public void show() {
        for (EntityType<?> et : getEntities()) {
            show(et);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EntityModel em = new EntityModel();
        em.show();
    }

}
