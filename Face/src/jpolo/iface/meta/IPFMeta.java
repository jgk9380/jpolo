package jpolo.iface.meta;

import annotation.SelectScope;

import assist.utils.DebugUtils1;
import assist.utils.PoloUtils;

import java.lang.reflect.Field;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;


public interface IPFMeta<P, T> {

    public IPMeta<P> getPoloMeta();

    public Field getField();

    public String getName();

    public String getLabel();

    public IPFMeta.PFType getType() throws Exception;

    public Class<?> getElementClass(); //如果是class，set，list类型 其他为空

    public boolean isSelectAble();

    SelectScope getSelectScope();

    enum PFType {
        String,
        Number,
        Date,
        Clob,
        Blob,
        Boolea,
        Enum,
        One,
        Many;

        public static PFType getFieldType(Field f) throws Exception {
            if (PoloUtils.isPolo(f.getType()))
                return One;
            if (f.getType().equals(List.class) || f.getType().equals(Set.class))
                return Many;
            if (f.getType().isEnum())
                return Enum;

            if (f.getType().equals(String.class)) {
                Column c = f.getAnnotation(Column.class);
                //System.out.println("ccolumn.len="+c.length());
                if (c != null && c.length() != 255 && c.length() > 50)
                    return Clob;
                return String;
            }
            if (f.getType().equals(Date.class))
                return Date;
            if (f.getType().equals(BigDecimal.class))
                return Number;
            if (f.getType().equals(Integer.class))
                return Number;
            if (f.getType().equals(java.lang.Number.class))
                return Number;
            if (f.getType().equals(Boolean.class) || f.getType().equals(boolean.class))
                return Boolea;
            if (f.getType().getSimpleName().equals("byte[]"))
                return Blob;

            DebugUtils1.error("错误的数据类型：" + f.getType().getName(), null);
            return null;
        }
    }
}
