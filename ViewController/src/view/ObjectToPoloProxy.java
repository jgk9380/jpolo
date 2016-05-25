package view;

import assist.utils.EntityManagerFactoryProxy;

import jpolo.iface.polo.IPolo;
import jpolo.iface.polo.IPoloField;

import jpolo.impl.polo.Polo;

import web.JSFUtils;

public class ObjectToPoloProxy {
    public ObjectToPoloProxy() {
        super();
    }

    public IPolo<?> getPolo(Object o) {
        if(o==null ) return null;
        IPolo<?> res = new Polo<>(EntityManagerFactoryProxy.getEntityManagerFor11g(), o);
        return res;
    }

    public IPoloField getPoloField(Object o, String name) {
        if(o==null ) return null;
        IPolo<?> res = new Polo<>(EntityManagerFactoryProxy.getEntityManagerFor11g(), o);
        try {
            return res.getPoloField(name);
        } catch (Exception e) {
            JSFUtils.addFacesErrorMessage(e.getMessage()+"£º´íÎóµÄÊý¾Ý");
            e.printStackTrace();
        }
        return null;
    }
}
