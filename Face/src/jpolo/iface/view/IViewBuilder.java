package jpolo.iface.view;

import java.util.List;

import javax.faces.model.SelectItem;

import javax.persistence.TypedQuery;

import jpolo.iface.meta.IPFMeta;


public interface IViewBuilder<T> {

    public TypedQuery<T> getQuery();

    String getJql();

    void setJql(String jql);

    String getFieldName();

    void setFieldName(String name);

    String getOper();

    void setOper(String name);

    Object getValue();

    void setValue(Object o);

    Object getValue2();

    void setValue2(Object o);

    List<String> getTableShowFields();

    void setTableShowFields(List<String> l);

    List<SelectItem> getQueryFieldSelectItemList();

    List<SelectItem> getOperSelectItemList();

    List<SelectItem> getTableFieldSelectItemList();

    IPFMeta.PFType getFieldType(String name);
}
