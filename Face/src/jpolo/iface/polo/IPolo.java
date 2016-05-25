package jpolo.iface.polo;

import javax.persistence.EntityManager;

import jpolo.iface.meta.IPMeta;


public interface IPolo<T> {//缓存 Map<name ,pk,Class<T> >封装polo对象的操作
    IPMeta<T> getMeta() throws  Exception;
    EntityManager getEm();
    T getPolobject() throws Exception;  
    IPoloField getPoloField(String name) throws Exception;
    void show() throws  Exception;
    Object getPK();
    String getTilte();//toString
    boolean isEditable();
    void save();
    void refresh();
}
