package jpolo.impl.polo.field;

import annotation.SelectScope;

import assist.utils.DataUtils;
import assist.utils.EntityManagerFactoryProxy;
import assist.utils.PoloUtils;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import jpolo.iface.polo.IPolo;

public class PoloListField extends PoloField {
    public PoloListField(IPolo polo, String name) {
        super(polo, name);
    }

    @Override
    public List getValue() {
        List res = null;
        try {
            res = (List) PoloUtils.getter(polo.getPolobject(), name);
            if (res != null)
               res.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<SelectItem> getSelectItem() throws Exception {
        SelectScope sc = getMeta().getSelectScope();
        Class fieldType = this.getMeta().getElementClass();
        String hql = null;
        if (sc == null||sc.hql().length()<1) {
            hql = " from " + EntityManagerFactoryProxy.getPoloMeta(fieldType).getName() + " o ";
        } else {
            hql = sc.hql();
        }
        System.out.println("hql="+hql);

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


    @Override
    public void show(int len) throws IllegalAccessException, InvocationTargetException, Exception {
        System.out.println(DataUtils.repeatBlank(len + 5 - this.getName().length()) + this.getName() + " = List[" +
                           this.getValue().size() + "] type=" + this.getMeta().getElementClass().getSimpleName());
    }
}
