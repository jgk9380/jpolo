package jpolo.impl.meta;

import annotation.Label;

import assist.utils.PoloUtils;

import entity.Employees;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.metamodel.EntityType;

import jpolo.iface.meta.IPFMeta;
import jpolo.iface.meta.IPMeta;


public class PoloMeta<T> implements IPMeta<T> {
    EntityManagerFactory emf;
    private Class<T> poloClass;
    private Map<String, IPFMeta> IPFMetasMap;
    
    //按照字段出现的顺序
    private List<String> nameList;
    private EntityType<T> et; 


    public PoloMeta(EntityManagerFactory emf, Class<T> clss) {
        this.emf = emf;
        poloClass = clss;
        et = emf.getMetamodel().entity(clss);
        try {
            initColsMetas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initColsMetas() throws NoSuchMethodException, Exception {
        IPFMetasMap = new HashMap<>();
        nameList = new ArrayList<>();
        Field[] fa = poloClass.getDeclaredFields();
        //System.out.println("fa.size="+fa.length);
        for (Field f : fa) {
            if (PoloUtils.getGetMethod(poloClass, f.getName()) != null &&
                PoloUtils.getSetMethod(poloClass, f.getName(), f.getType()) != null) {
                IPFMeta colMeta = new PoloFieldMeta(this, f.getName());
                IPFMetasMap.put(f.getName(), colMeta);
                nameList.add(f.getName());
            }
        }
    }

    @Override
    public String getAlias() {
        String alias;
        Label aliasAnno = poloClass.getAnnotation(Label.class);
        if (aliasAnno != null)
            alias = aliasAnno.value();
        else
            alias = poloClass.getSimpleName();
        return alias;
    }

    @Override
    public String getName() {
        String name;
        Entity entity = poloClass.getAnnotation(Entity.class);
        if (entity != null && entity.name().length() > 1)
            name = entity.name();
        else
            name = poloClass.getSimpleName();      
        return name;
    }

    @Override
    public IPFMeta getFMeta(String name) {
        IPFMeta ic = IPFMetasMap.get(name);
        if (ic == null) {
            System.out.println("entity:" + this.getName() + " prop:" + name + " is null");
        }
        return ic;
    }


    @Override
    public List<String> getFNames() {
        return nameList;
    }

    @Override
    public Class<T> getPoloClass() {
        return poloClass;
    }


    @Override
    public Class<?> getIDClass() {
        IdClass ic = poloClass.getAnnotation(IdClass.class);
        if (ic != null)
            return ic.value();
        return null;
    }

    @Override
    public String getIDProperty() {
        String res = null;
        int i = 0;
        for (IPFMeta ipm : IPFMetasMap.values()) {
            if (ipm.getField().getAnnotation(Id.class) != null) {
                res = ipm.getName();
                i++;
            }
        }
        if (i == 1)
            return res;
        return null;
    }

    public static void main(String[] args) throws Exception {
        PoloMeta<Employees> pm = new PoloMeta<>(null, Employees.class);
        //System.out.println(pm.getFMeta("loginUsersList").getSelectItems(null).keySet().size());
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
