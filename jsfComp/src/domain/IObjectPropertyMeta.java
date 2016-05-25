package domain;

import org.apache.myfaces.trinidad.component.UIXInput;

public interface IObjectPropertyMeta {
    boolean isUpdatAble();
    String getLabel();
    /**RichInputText,selectMany,shutttle,inputText ,inputNumber,intputDate,selectBoolean*/
    Class<? extends UIXInput> getComponentType();
}
