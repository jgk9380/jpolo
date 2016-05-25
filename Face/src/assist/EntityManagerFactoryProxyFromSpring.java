package assist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerFactoryProxyFromSpring {
    @PersistenceContext
    EntityManager em;
    public EntityManagerFactoryProxyFromSpring() {
        super();
    }

    public EntityManager getEm() {
        return em;
    }
}
