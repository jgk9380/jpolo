package jpolo.impl.polo;

import javax.persistence.EntityManager;

public class NewPolo<T> extends Polo<T> {
    public NewPolo(EntityManager em,Class<T> clazz) {
        super(em, null);
        try {
            super.po = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean isEditable() {       
            return true;     
    }
}
