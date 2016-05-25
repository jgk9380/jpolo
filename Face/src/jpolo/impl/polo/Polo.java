package jpolo.impl.polo;

import annotation.Editable;

import assist.utils.EntityManagerFactoryProxy;
import assist.utils.PoloUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import jpolo.iface.meta.IPMeta;
import jpolo.iface.polo.IPolo;
import jpolo.iface.polo.IPoloField;

import jpolo.impl.polo.field.PoloEntityField;
import jpolo.impl.polo.field.PoloEnumField;
import jpolo.impl.polo.field.PoloField;
import jpolo.impl.polo.field.PoloListField;
import jpolo.impl.polo.field.PoloSetField;


public class Polo<T> implements IPolo<T> {
    T po;
    EntityManager em;
    Map<String, IPoloField> pfMap = new HashMap<>();

    public Polo(EntityManager em, T po) {
        this.em = em;
        this.po = po;
    }

    @Override
    public T getPolobject() throws Exception {
        return po;
    }


    @Override
    public IPoloField getPoloField(String name) throws Exception {

        if (!pfMap.containsKey(name)) {
            IPoloField ipf = null;
            Class fieldType = this.getMeta().getFMeta(name).getField().getType();
            if (fieldType.equals(Set.class)) {
                ipf = new PoloSetField(this, name);
            } else if (fieldType.equals(List.class)) {
                ipf = new PoloListField(this, name);
            } else if (fieldType.isEnum()) {
                ipf = new PoloEnumField(this, name);
            } else if (PoloUtils.isPolo(fieldType)) {
                ipf = new PoloEntityField(this, name);
            } else {
                ipf = new PoloField(this, name);
            }
            pfMap.put(name, ipf);
        }
        return pfMap.get(name);
    }


    @Override
    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.semantic-warning" })
    public IPMeta<T> getMeta() {
        EntityManagerFactoryProxy emfp=new EntityManagerFactoryProxy();
        //System.out.println("po="+po);
        
        return emfp.getPoloMeta((Class<T>)po.getClass());
    }

    @Override
    public void show() throws Exception {
        System.out.println("" + this.getPolobject().getClass().getSimpleName() + ":");
        System.out.println("    {");
        int maxLen = 0;
        for (String name : getMeta().getFNames()) {
            if (name.length() > maxLen)
                maxLen = name.length();
        }
        for (String name : getMeta().getFNames()) {
            this.getPoloField(name).show(maxLen);
        }
        System.out.println("    }");
        System.out.println("   ");
    }

    @Override
    public Object getPK() {
        return this.getEm().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(po);
    }

    @Override
    public String getTilte() {
        return po.toString();
    }

    @Override
    public EntityManager getEm() {
        return em;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Polo)) {
            return false;
        }
        final Polo other = (Polo) object;
        if (!(po == null ? other.po == null : po.equals(other.po))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((po == null) ? 0 : po.hashCode());
        return result;
    }

    @Override
    public boolean isEditable() {
        Editable ve = this.getMeta().getPoloClass().getAnnotation(Editable.class);
        if (ve == null)
            return true;
        return ve.value();
    }

    @Override
    public void save() {
        em.merge(po);
    }

    @Override
    public void refresh() {
        em.refresh(po);
    }
}
