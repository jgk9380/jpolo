package jpolo.impl.meta;

import annotation.Label;
import annotation.SelectScope;

import assist.utils.PoloUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import jpolo.iface.meta.IPFMeta;
import jpolo.iface.meta.IPMeta;


public class PoloFieldMeta implements IPFMeta {

   
    Field field;
    IPMeta PoloMeta;//含有EntityManagerFactory信息    
    String name;
    
    public PoloFieldMeta(IPMeta ip,String name) throws NoSuchFieldException { //column初始化
        this.PoloMeta=ip;
        this.name=name;
//        System.out.println("pfn="+name);
//        System.out.println("pfc="+ip.getPoloClass());
        field = ip.getPoloClass().getDeclaredField(name);
          //EntityType    et = ip.getEntityManagerFactory().getMetamodel().entity(ip.getPoloClass());
    }

    @Override
    public IPMeta getPoloMeta() {
        //return EntityManagerFactoryProxy.getPoloMeta(field.getClass());
        return PoloMeta;
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public String toString() {
        String res = null;
        res = "field:" + field.getName() + " of class:" + this.getPoloMeta().getName();
        return res;
    }

    @Override
    public String getLabel() {
        String label;
        Label labelAnno = field.getAnnotation(Label.class);
        if (labelAnno != null)
            label = labelAnno.value();
        else
            label = field.getName();
        return label;
    }

    @Override
    public IPFMeta.PFType getType() throws Exception {
        return jpolo.iface.meta.IPFMeta.PFType.getFieldType(field);
    }

    @Override
    public boolean isSelectAble() {      
        if (PoloUtils.isPolo(field.getType())) //ManyToOne OneToOne
            return true;
        if (field.getType().isEnum()) //ManyToOne OneToOne
            return true;
        if (this.getElementClass() != null) //OneToMany ManyToMany
            return true;
        if (field.getAnnotation(SelectScope.class) != null) //普通属性
            return true;
        
        return false;
    }

    @Override
    public Class<?> getElementClass() {
        Type type = field.getGenericType();
        Class elementType = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] actualTypes = paramType.getActualTypeArguments();
            elementType = (Class) actualTypes[0]; //list只有一个泛型类型
        }
        return elementType;
    }

    @Override
    public Field getField() {
        return field;
    }
//
//    @Override
//    public Map<String, Object> getSelectItems(IPolo po) throws Exception {
//        Map<String, Object> mapres = new HashMap<>();
//        if (!this.isSelectAble())
//            return mapres;
//        SelectScope sc = this.getField().getAnnotation(SelectScope.class);
//        if (this.getType().equals(IPFMeta.PFType.One) || this.getType().equals(IPFMeta.PFType.Many)) {
//            List<?> templ = null;
//            Class<?> clazz = this.getElementClass();
//            if (sc == null || sc.hql().length()==0) {
//                templ =
//                    EntityManagerFactoryProxy.getEntityManagerFor11g().createQuery("select o from " + clazz.getSimpleName() + " o",
//                                                             clazz).getResultList();
//            } else if(sc.sql().length()>1){
//                //TODO 是否含[]
//                templ = EntityManagerFactoryProxy.getEntityManagerFor11g().createQuery(sc.hql(), clazz).getResultList();
//            }
//            for (Object o : templ) {
//                mapres.put(o.toString(), o);
//            }
//            return mapres;
//        } else {
//            //TODO 是否含[]
//            List<?> l = EntityManagerFactoryProxy.getEntityManagerFor11g().createNativeQuery(sc.sql()).getResultList();
//            for(Object o:l){
//                Object[] oa = (Object[]) o;
//                mapres.put(oa[0].toString(), oa[1]);
//            }
//            return mapres;
//        }
//    }
    @Override
    public SelectScope getSelectScope() {
        SelectScope sc = this.getField().getAnnotation(SelectScope.class);
        return sc;
    }
}
