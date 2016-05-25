package domain;

import java.util.List;

public interface IObjectProxy<O>{ //O为object类型，P为主键类型
    //对象属性
    O getObject();
    Object getID();  
    String toString();//lable
    IObjectPropertyProxy<?,O> getProperty(String name);   
}
