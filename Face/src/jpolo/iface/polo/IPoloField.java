package jpolo.iface.polo;

import java.util.List;

import javax.faces.model.SelectItem;

import jpolo.iface.meta.IPFMeta;


public interface IPoloField {
    IPFMeta getMeta() throws Exception;

    IPolo getPolo();

    String getName();

    Object getValue();

    void setValue(Object v) ;

    void show(int len) throws Exception;

    boolean isEditable() throws Exception;
    
    List<SelectItem> getSelectItem() throws Exception;
}
