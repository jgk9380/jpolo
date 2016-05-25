package domain;

import editor.IEditor;

import org.apache.myfaces.trinidad.component.UIXInput;

public interface IObjectPropertyProxy<P,O> {
    //对象属性 P为属性，O为对象类型
    IObjectProxy<O> getObjectProxy();
    P getValue();
    void setValue(P value);
    UIXInput getComponent();
}
