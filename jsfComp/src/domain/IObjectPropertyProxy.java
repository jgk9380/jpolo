package domain;

import editor.IEditor;

import org.apache.myfaces.trinidad.component.UIXInput;

public interface IObjectPropertyProxy<P,O> {
    //�������� PΪ���ԣ�OΪ��������
    IObjectProxy<O> getObjectProxy();
    P getValue();
    void setValue(P value);
    UIXInput getComponent();
}
