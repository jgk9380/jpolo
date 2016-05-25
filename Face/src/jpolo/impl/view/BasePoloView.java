package jpolo.impl.view;

import assist.utils.EntityManagerFactoryProxy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import jpolo.iface.meta.IPMeta;
import jpolo.iface.polo.IPolo;
import jpolo.iface.view.IBasePoloView;
import jpolo.iface.view.IViewBuilder;

import jpolo.impl.meta.PoloMeta;
import jpolo.impl.polo.Polo;

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

public class BasePoloView<T> implements IBasePoloView<T> {
    List<IPolo<T>> list;
    EntityManager em;
    int currentPos=0;
    Class<T> clazz;
    IViewBuilder<T> viewBuilder;

    public BasePoloView(EntityManager em, Class<T> clazz) throws NoSuchFieldException, Exception {
        super();
        this.em = em;
        this.clazz = clazz;
        viewBuilder = new ViewBuilder<>(em, clazz);
        this.refresh();
    }

    public Class getGenericType() {
        Type genType = this.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        System.out.println("params.len=" + params.length);
        System.out.println("params.len=" + params[0]);
        sun.reflect.generics.reflectiveObjects.TypeVariableImpl tvi = (TypeVariableImpl) params[0];
        return (Class) params[0];
    }

    @Override
    public IPMeta<T> getPMeta() {
        return EntityManagerFactoryProxy.getPoloMeta(clazz);
    }

    @Override
    public IPolo<T> getCurrent() {
        if (currentPos >= list.size())
            return null;
        return list.get(currentPos);
    }

    @Override
    public void setCurrent(IPolo<T> o) {
        if (list.contains(o)) {
            currentPos = list.indexOf(o);
        }
    }

    @Override
    public Iterator<IPolo<T>> getIterator() {
        return list.iterator();
    }

    @Override
    public List<IPolo<T>> getPoloList() {
        return list;
    }

    @Override
    public void refresh() {
        em.clear();
        currentPos = 0;
        List<T> templ = this.viewBuilder.getQuery().getResultList();
        list = new ArrayList<>();
        for (T oa : templ) {
            list.add(new Polo<>(em, oa));
        }
    }

    @Override
    public IViewBuilder<T> getViewBuilder() {        
        return viewBuilder;
    }

   
}
