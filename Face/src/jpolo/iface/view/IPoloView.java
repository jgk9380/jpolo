package jpolo.iface.view;

import jpolo.iface.polo.IPolo;


/**
 * @param <T>
 */
public interface IPoloView<T> extends IBasePoloView<T> {
    boolean isEditable();//
    boolean isNewable();//；@viewNewAble 默认为viewEditable
    boolean isDeletable();//；@viewdeleteAble 默认为viewEdiate
    void delete(IPolo<T> ip);
    IPolo<T> newPOlO();
    void commit() throws Exception;
    void rollback() throws Exception;   
    void clear();
}
