package jpolo.iface.view;

import jpolo.iface.polo.IPolo;


/**
 * @param <T>
 */
public interface IPoloView<T> extends IBasePoloView<T> {
    boolean isEditable();//
    boolean isNewable();//��@viewNewAble Ĭ��ΪviewEditable
    boolean isDeletable();//��@viewdeleteAble Ĭ��ΪviewEdiate
    void delete(IPolo<T> ip);
    IPolo<T> newPOlO();
    void commit() throws Exception;
    void rollback() throws Exception;   
    void clear();
}
