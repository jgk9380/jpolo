package jpolo.impl.view;

import assist.utils.EntityManagerFactoryProxy;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import jpolo.iface.meta.IPFMeta;
import jpolo.iface.meta.IPMeta;
import jpolo.iface.view.IViewBuilder;
import entity.JMenu;

public class ViewBuilder<T> implements IViewBuilder<T> {
    Class<T> clazz;
    EntityManager em;
    List<String> tableShowFieldList;
    String jql;

    String fieldName, oper;
    Object value, value2;


    List<SelectItem> operSelectItemList;
    List<SelectItem> queryFieldSelectItemList;
    List<SelectItem> tableFieldSelectItemList;

    public ViewBuilder(EntityManager em, Class<T> clazz) throws Exception {
        super();
        this.em = em;
        this.clazz = clazz;
        this.initTableShowFieldList();
        this.initOperSelectItemList();
        this.initTableFieldSelectItemList();
        this.initQueryFieldSelectItemList();
    }

    private void initTableShowFieldList() throws Exception {
        tableShowFieldList = new ArrayList<>();
        IPMeta<T> ipm = EntityManagerFactoryProxy.getPoloMeta(clazz);
        
        for (String name : ipm.getFNames()) {
            if (!ipm.getFMeta(name).getType().equals(IPFMeta.PFType.Many) &&
                !ipm.getFMeta(name).getType().equals(IPFMeta.PFType.Blob) &&
                !ipm.getFMeta(name).getType().equals(IPFMeta.PFType.Clob)) {
                tableShowFieldList.add(name);
            }
        }
    }

    private String getQueryString(String fieldName, String oper) {
        String res = "select o  from " + clazz.getSimpleName() + " o where";
        if (oper.equalsIgnoreCase("between")) {
            res = res + " o." + fieldName + " " + oper + " :" + fieldName + "1" + " and :" + fieldName + "2";
            return res;
        }
        res = res + " o." + fieldName + " " + oper + " :" + fieldName;

        return res;
    }

    @Override
    public TypedQuery<T> getQuery() {
        /**
         * 1、如果有jql，取jql
         * 2、如果没有选择字段，取默认语句
         * 3、按字段查询
         * */
        TypedQuery<T> query;        
        if (this.getJql() != null&&this.getJql().length()>5) {
            query = em.createQuery(this.getJql(), clazz);
            return query;
        }

        if (this.getFieldName() == null || this.getOper() == null) {
            query = em.createQuery("select o from " + clazz.getName() + " o", clazz);
            query.setMaxResults(500);
            query.setFirstResult(0);
            return query;
        }

        String tempjql = this.getQueryString(fieldName, oper);
        query = em.createQuery(tempjql, clazz);
        if (oper.equalsIgnoreCase("between")) {
            query.setParameter(fieldName + "1", value);
            query.setParameter(fieldName + "2", value2);
        } else {
            query.setParameter(fieldName, value);
        }
        return query;
    }

    @Override
    public String getJql() {
        return jql;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getOper() {
        return oper;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getValue2() {
        return value2;
    }


    private List<T> getList() {
        System.out.println("jql=" + getQuery().toString());
        return this.getQuery().getResultList();
    }

    @Override
    public void setJql(String jql) {
        this.jql = jql;
    }

    @Override
    public void setFieldName(String name) {
        fieldName = name;
    }

    @Override
    public void setOper(String op) {
        oper = op;
    }

    @Override
    public void setValue(Object o) {
        value = o;
    }

    @Override
    public void setValue2(Object o) {
        value2 = o;
    }

    @Override
    public List<String> getTableShowFields() {
        return tableShowFieldList;
    }

    @Override
    public void setTableShowFields(List<String> l) {
        tableShowFieldList = l;
    }

    @Override
    public IPFMeta.PFType getFieldType(String name) {
        try {
            return EntityManagerFactoryProxy.getPoloMeta(clazz).getFMeta(name).getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initTableFieldSelectItemList() {
        tableFieldSelectItemList = new ArrayList<>();
        new ArrayList<>();
        IPMeta ipm = EntityManagerFactoryProxy.getPoloMeta(clazz);
        for (String name : EntityManagerFactoryProxy.getPoloMeta(clazz).getFNames()) {
            String label = EntityManagerFactoryProxy.getPoloMeta(clazz).getFMeta(name).getLabel();
                        if (!label.equals(name))
                            label = label + " :" + name;
            SelectItem si = new SelectItem();
            si.setLabel(label);
            si.setValue(name);
            tableFieldSelectItemList.add(si);
        }
        return;
    }

    @Override
    public List<SelectItem> getTableFieldSelectItemList() {
        return this.tableFieldSelectItemList;
    }

    private void initQueryFieldSelectItemList() throws Exception {
        queryFieldSelectItemList = new ArrayList<>();
        new ArrayList<>();
        IPMeta<T> ipm = EntityManagerFactoryProxy.getPoloMeta(clazz);
        for (String name : ipm.getFNames()) {
            if (!ipm.getFMeta(name).getType().equals(IPFMeta.PFType.Many) &&
                !ipm.getFMeta(name).getType().equals(IPFMeta.PFType.One)) {
                String label = EntityManagerFactoryProxy.getPoloMeta(clazz).getFMeta(name).getLabel();
                //                if (!label.equals(name))
                //                    label = label + ":" + name;
                SelectItem si = new SelectItem();
                si.setLabel(label);
                si.setValue(name);
                queryFieldSelectItemList.add(si);
            }
        }
        return;
    }

    @Override
    public List<SelectItem> getQueryFieldSelectItemList() {
        return queryFieldSelectItemList;
    }

    private void initOperSelectItemList() {
        operSelectItemList = new ArrayList<>();
        operSelectItemList.add(new SelectItem(">"));
        operSelectItemList.add(new SelectItem("<"));
        operSelectItemList.add(new SelectItem("="));
        operSelectItemList.add(new SelectItem("like"));
        operSelectItemList.add(new SelectItem("between"));

    }

    @Override
    public List<SelectItem> getOperSelectItemList() {
        return this.operSelectItemList;
    }


}
