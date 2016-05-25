package proxy.test.a;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestBean {
    public TestBean() {
        super();
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        User user = new User();
        user.setId(11);
        user.setName("jackeychow");
        //user.setAddress("hust");
        // 用BeanInfo得到各属性的值
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(user.getClass());
            
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < pds.length; i++) {
                String propertyName = pds[i].getName();
                System.out.println(propertyName+" r="+pds[i].getReadMethod()+"  w="+pds[i].getWriteMethod()+" isassinged="+pds[i].isPreferred());
                System.out.println(pds[i].getValue(""));
                
//                if (!propertyName.equals("class")) {
//                    Method method = pds[i].getReadMethod();
//                    Object value = method.invoke(user);
//                    System.out.println(propertyName + ":" + value);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
       
        }
        Class<User> userClass = (Class<User>) user.getClass();
        Field[] fields = userClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            Method method = null;
            try {
                //                method = userClass.getDeclaredMethod("get" //得到属性fieldName的get方法,由于get方法里没有参数,所以第二个参数为空
                //                                                     + StringUtils.firstToUpper(fieldName));
                //                Object obj = method.invoke(user); //调用get方法，传入的参数为空
                //                System.out.println(fieldName + "2222:" + obj);
            } catch (SecurityException e) {
                e.printStackTrace();

            }
        }
    }
}
