package jpolo.impl.view;

import annotation.Editable;
import annotation.ViewAddable;
import annotation.ViewDeletable;

import assist.utils.EntityManagerFactoryProxy;

import entity.Employees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jpolo.iface.polo.IPolo;
import jpolo.iface.view.IPoloView;

import jpolo.impl.polo.NewPolo;

/**
 * @param <T>
 */
public class PoloView<T> extends BasePoloView<T> implements IPoloView<T> {
    List<IPolo<T>> addList;
    List<IPolo<T>> delList;


    public PoloView(EntityManager em, Class<T> clazz) throws NoSuchFieldException, Exception {
        super(em, clazz);
        addList = new ArrayList<>();
        delList = new ArrayList<>();
    }


    public void show(int i) throws Exception {
        int j = 0;
        Iterator<IPolo<T>> it = this.getIterator();
        while (it.hasNext()) {
            IPolo ip = it.next();
            ip.show();
            j++;
            if (j > i)
                return;
        }
    }


    @Override
    public boolean isEditable() {
        Editable ve = super.clazz.getAnnotation(Editable.class);
        if (ve == null)
            return true;
        return ve.value();
    }

    @Override
    public boolean isNewable() {
        if (this.isEditable() == false)
            return false;
        ViewAddable ve = super.clazz.getAnnotation(ViewAddable.class);
        if (ve == null)
            return true;
        return ve.value();
    }

    @Override
    public boolean isDeletable() {
        if (this.isEditable() == false)
            return false;
        ViewDeletable ve = super.clazz.getAnnotation(ViewDeletable.class);
        if (ve == null)
            return true;
        return ve.value();
    }

    @Override
    public void delete(IPolo<T> ip) {
        addList.remove(ip);
        list.remove(ip);
        delList.add(ip);

    }

    @Override
    public IPolo<T> newPOlO() {
        IPolo<T> ip = new NewPolo<>(em, clazz);
        System.out.println("ip=" + ip);
        addList.add(ip);
        list.add(0, ip); //getCurrent();
        return ip;
    }


    public static void main(String[] args) throws NoSuchFieldException, Exception {
        PoloView<Employees> vs = new PoloView<>(EntityManagerFactoryProxy.getEntityManagerFor11g(), Employees.class);
        for (IPolo ip : vs.getPoloList()) {
            ip.show();
        }
    }

    @Override
    public void commit() throws Exception {
        EntityTransaction transaction = null;
        transaction = em.getTransaction();
        if (transaction.isActive())
            transaction.rollback();
        transaction.begin();
        for (IPolo ip : delList) {
            em.remove(em.merge(ip.getPolobject()));
        }
        for (IPolo ip : addList) {
            em.persist(ip.getPolobject());
        }
        for (IPolo ip : list) {
            em.merge(ip.getPolobject());
        }
        transaction.commit();
        delList.clear();
        addList.clear();
    }

    @Override
    public void rollback() throws Exception {
        em.clear();
        delList = new ArrayList<>();
        addList = new ArrayList<>();
        this.refresh();
    }


    @Override
    public void clear() {
        em.clear();
    }
}
