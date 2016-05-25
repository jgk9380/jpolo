package assist.utils;

import entity.JRole;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Entity;


public class PoloUtils {


    //取值
    public static void setter(Object obj, String att, Object value, Class<?> type) throws Exception {
        Method method = null;
        method = obj.getClass().getMethod("set" + DataUtils.upperFirst(att), type);
        method.invoke(obj, value);

    }
    //赋值
    public static Object getter(Object obj, String att)  {
        Method method = null;
        try {
            method = obj.getClass().getMethod("get" + DataUtils.upperFirst(att));
            return method.invoke(obj);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getGetMethod(Class<?> clazz, String att)  {
        Method method = null;
        try {
            method =clazz.getMethod("get" + DataUtils.upperFirst(att));
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            return null;
        }
        return method;
    }
    
    public static Method getSetMethod(Class<?> clazz, String att,Class<?>... parameterTypes)  {
        Method method = null;
        try {
            method = clazz.getMethod("set" + DataUtils.upperFirst(att),parameterTypes);
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            return null;
        }
        return method;
    }
    
    //打印对象
    public static void printObject(Object o) throws Exception {
        if (o == null) {
            System.out.println("【Object is  null】");
            return;
        }
        String res = "***printObject: class=" + o.getClass() + "***\n【\n";
        {
            Field[] fa = o.getClass().getDeclaredFields();
            for (Field f : fa) {
                Method m = getGetMethod(o.getClass(), f.getName());
                if (m != null) {
                    String temp =
                        DataUtils.repeatBlank(20 - f.getName().length()) + f.getName() + " = " + getter(o, f.getName());
                    temp = temp + DataUtils.repeatBlank(35 - temp.length()) + "type = " + f.getType().getSimpleName();
                    res = res + temp + "\n";
                }
            }
        }
        res = res + "】\n";
        System.out.println(res);
    }
    //取对象的主键


    //判断是否为实体对象
    public static boolean isPolo(Class<?> clzz) {
        if (clzz.getAnnotation(Entity.class) != null)
            return true;
        return false;
    }


    public static void main(String[] args) {
        System.out.println(PoloUtils.isPolo(JRole.class));
        System.out.println(PoloUtils.isPolo(String.class));
    }
}
