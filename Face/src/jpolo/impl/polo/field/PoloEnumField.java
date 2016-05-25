package jpolo.impl.polo.field;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import jpolo.iface.polo.IPolo;

public class PoloEnumField extends PoloField {
    public PoloEnumField(IPolo polo, String name) {
        super(polo, name);
    }

    @Override
    public List<SelectItem> getSelectItem() throws Exception {
        Class fieldType = this.getMeta().getField().getType();
        //java.lang.annotation.ElementType
        Enum[] oa = (Enum[]) fieldType.getEnumConstants();
        List<SelectItem> res = new ArrayList<>();
        Enumerated enumerated = (Enumerated) this.getMeta().getField().getAnnotation(Enumerated.class);
        EnumType et = null;
        if (enumerated != null) {
            et = enumerated.value();
        }
        for (Enum o : oa) {
            //System.out.println("o=" + o.ordinal() + " o.name" + o.name() + "  et=" + et);
            SelectItem si = new SelectItem();
            si.setLabel(o.name());
            si.setValue(o);
            res.add(si);
        }

        return res;
    }
}
