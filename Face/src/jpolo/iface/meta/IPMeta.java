package jpolo.iface.meta;

import java.util.List;

import javax.persistence.EntityManagerFactory;


public interface IPMeta<T> {
    
    String getName();

    Class<T> getPoloClass();

    String getAlias();

    IPFMeta getFMeta(String name);

    List<String> getFNames();
    
    Class<?> getIDClass();
    
    String getIDProperty();   
    
    EntityManagerFactory getEntityManagerFactory();
}
