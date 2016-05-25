package jpolo.iface.view;

import java.util.Iterator;
import java.util.List;

import jpolo.iface.meta.IPMeta;
import jpolo.iface.polo.IPolo;

public interface IBasePoloView<T> {
    IPMeta getPMeta();     
    Iterator<IPolo<T>> getIterator();
    List<IPolo<T>> getPoloList();
    IPolo<T> getCurrent(); 
    void setCurrent(IPolo<T> o);  
    void refresh();
    IViewBuilder<T> getViewBuilder();
   
}
