package domain;

import java.util.List;

public interface IObjectProxy<O>{ //OΪobject���ͣ�PΪ��������
    //��������
    O getObject();
    Object getID();  
    String toString();//lable
    IObjectPropertyProxy<?,O> getProperty(String name);   
}
