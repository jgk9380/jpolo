package proxy.test;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

public class BeanInfoUtil {

    public static void setProperty(UserInfo userInfo, String propertyName,Object propertyValue) throws Exception {
        PropertyDescriptor propDesc = new PropertyDescriptor(propertyName, UserInfo.class);
        Method methodSetUserName = propDesc.getWriteMethod();
        methodSetUserName.invoke(userInfo, propertyValue);      
    }

    public static Object getProperty(UserInfo userInfo, String propertyName) throws Exception {
        PropertyDescriptor proDescriptor = new PropertyDescriptor(propertyName, UserInfo.class);
        Method methodGetUserName = proDescriptor.getReadMethod();
        Object res = methodGetUserName.invoke(userInfo);
        return res;
    }

    public static void setPropertyByIntrospector(UserInfo userInfo, String propertyName,Object propertyValue) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
                if (propDesc.getName().equals(propertyName)) {
                    Method methodSetUserName = propDesc.getWriteMethod();
                    methodSetUserName.invoke(userInfo, propertyValue);                   
                    break;
                }
            }
        }
    }

    public static Object getPropertyByIntrospector(UserInfo userInfo, String propertyName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
                if (propDesc.getName().equals(propertyName)) {
                    Method methodGetUserName = propDesc.getReadMethod();
                    Object res = methodGetUserName.invoke(userInfo);
                    return res;
                
                }
            }
        }
        return null;
    }
}

//通过这两个类的比较可以看出，都是需要获得PropertyDescriptor，只是方式不一样：前者通过创建对象直接获得，后者需要遍历，
//所以使用PropertyDescriptor类更加方便。