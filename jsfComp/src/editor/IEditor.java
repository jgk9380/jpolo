package editor;

import javax.faces.component.UIComponent;


import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;


import org.apache.myfaces.trinidad.component.UIXInput;

/**
 * @param <T>
 * ������ֶ����ԣ�className��fieldName
 * TODO ȡ��
 */
public interface IEditor<T> {
    //���໯
    /*RichInputText,selectMany,shutttle,inputText ,inputNumber,intputDate,selectBoolean*/
    Class<? extends UIXInput> getComponentType();
    UIXInput getComponent();
    
    /* 
    String getLable();
    int getRows();
    int getCols();
    boolean isRender();
    boolean isDisable();
    String getStyleClass();
    */
    
}
