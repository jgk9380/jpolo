package jpolo.impl.polo.field;

import annotation.Editable;
import annotation.SelectScope;

import assist.utils.DataUtils;
import assist.utils.PoloUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import javax.persistence.Id;

import jpolo.iface.meta.IPFMeta;
import jpolo.iface.polo.IPolo;
import jpolo.iface.polo.IPoloField;

import jpolo.impl.polo.NewPolo;

public class PoloField implements IPoloField {
    IPolo polo;
    String name;

    public PoloField(IPolo polo, String name) {
        this.polo = polo;
        this.name = name;
    }


    @Override
    public IPolo getPolo() {
        return polo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        try {
            return PoloUtils.getter(polo.getPolobject(), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setValue(Object v) {
        //System.out.println("set:" + v);
        Field f;
        try {
            f = polo.getPolobject().getClass().getDeclaredField(name);
            PoloUtils.setter(polo.getPolobject(), name, v, f.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public IPFMeta getMeta() throws NoSuchMethodException, Exception {
        return polo.getMeta().getFMeta(name);
    }


    @Override
    public void show(int len) throws IllegalAccessException, InvocationTargetException, Exception {
        System.out.println(DataUtils.repeatBlank(len + 5 - this.getName().length()) + this.getName() + " = " +
                           this.getValue());
    }

    @Override
    public boolean isEditable() throws Exception {
        Editable ea = getMeta().getField().getAnnotation(Editable.class);
        if (ea != null)
            return ea.value();

        if (this.getPolo() instanceof NewPolo)
            return true;

        if (getMeta().getField().getAnnotation(Id.class) != null)
            return false;

        return true;
    }

    @Override
    public List<SelectItem> getSelectItem() throws Exception {
        SelectScope sc = getMeta().getSelectScope();
        if (sc == null||sc.sql().length()<1)
            return Collections.emptyList();
        List<SelectItem> res = new ArrayList<>();
        String sql = sc.sql();
        List l = polo.getEm().createNativeQuery(sql).getResultList();
        for (Object o : l) {
            Object[] oa = (Object[]) o;
            SelectItem si = new SelectItem();
            si.setLabel(oa[0].toString());
            si.setValue(oa[1]);
            res.add(si);
        }
        return res;
    }
}
