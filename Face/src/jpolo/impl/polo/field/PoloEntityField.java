package jpolo.impl.polo.field;

import annotation.SelectScope;

import assist.utils.EntityManagerFactoryProxy;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import jpolo.iface.polo.IPolo;

public class PoloEntityField extends PoloField {
    public PoloEntityField(IPolo polo, String name) {
        super(polo, name);
    }


    @Override
    public List<SelectItem> getSelectItem() throws Exception {
        SelectScope sc = getMeta().getSelectScope();
        Class fieldType = this.getMeta().getField().getType();
        String hql = null;
        if (sc == null||sc.hql().length()<1) {
            hql = " from " + EntityManagerFactoryProxy.getPoloMeta(fieldType).getName() + " o ";
        } else {
            hql = sc.hql();
        }
        
        List l = polo.getEm().createQuery(hql, fieldType).getResultList();
        List<SelectItem> res = new ArrayList<>();
        for (Object o : l) {
            SelectItem si = new SelectItem();
            si.setLabel(o.toString());
            si.setValue(o);
            res.add(si);
        }
        return res;
    }


}
