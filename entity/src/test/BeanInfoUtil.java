package test;

import entity.Employees;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

public class BeanInfoUtil {   
    
    /**
     * 　PropertyDescriptor类:

　　PropertyDescriptor类表示JavaBean类通过存储器导出一个属性。主要方法：
    　　1. getPropertyType()，获得属性的Class对象;
    　　2. getReadMethod()，获得用于读取属性值的方法；getWriteMethod()，获得用于写入属性值的方法;
    　　3. hashCode()，获取对象的哈希值;
    　　4. setReadMethod(Method readMethod)，设置用于读取属性值的方法;
    　　5. setWriteMethod(Method writeMethod)，设置用于写入属性值的方法。
     */

  
    public static void setProperty(Employees userInfo,String userName)throws Exception{
        PropertyDescriptor propDesc=new PropertyDescriptor(userName,Employees.class);
        Method methodSetUserName=propDesc.getWriteMethod();
        methodSetUserName.invoke(userInfo, "wong");
        System.out.println("set userName:"+userInfo.getName());
    }
  
    public static void getProperty(Employees userInfo,String userName)throws Exception{
        PropertyDescriptor proDescriptor =new PropertyDescriptor(userName,Employees.class);
        Method methodGetUserName=proDescriptor.getReadMethod();
        Object objUserName=methodGetUserName.invoke(userInfo);
        System.out.println("get userName:"+objUserName.toString());
    }
    
    
/**Introspector类:

    　　将JavaBean中的属性封装起来进行操作。在程序把一个类当做JavaBean来看，就是调用Introspector.getBeanInfo()方法，得到的BeanInfo对象封装了把这个类当做JavaBean看的结果信息，即属性的信息。

    　　getPropertyDescriptors()，获得属性的描述，可以采用遍历BeanInfo的方法，来查找、设置类的属性。具体代码如下：*/
    
    public static void setPropertyByIntrospector(Employees userInfo,String userName)throws Exception{
          BeanInfo beanInfo=Introspector.getBeanInfo(Employees.class);
          PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();
          if(proDescrtptors!=null&&proDescrtptors.length>0){
              for(PropertyDescriptor propDesc:proDescrtptors){
                  if(propDesc.getName().equals(userName)){
                      Method methodSetUserName=propDesc.getWriteMethod();
                      methodSetUserName.invoke(userInfo, "alan");
                      System.out.println("set userName:"+userInfo.getName());
                      break;
                  }
              }
          }
      }
      
      public static void getPropertyByIntrospector(Employees userInfo,String userName)throws Exception{
          BeanInfo beanInfo=Introspector.getBeanInfo(Employees.class);
          PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();
          if(proDescrtptors!=null&&proDescrtptors.length>0){
              for(PropertyDescriptor propDesc:proDescrtptors){
                  if(propDesc.getName().equals(userName)){
                      Method methodGetUserName=propDesc.getReadMethod();
                      Object objUserName=methodGetUserName.invoke(userInfo);
                      System.out.println("get userName:"+objUserName.toString());
                      break;
                  }
              }
          }
      }
      
      // 通过这两个类的比较可以看出，都是需要获得PropertyDescriptor，只是方式不一样：前者通过创建对象直接获得，后者需要遍历，所以使用PropertyDescriptor类更加方便。
      
      
//      由上述可看出，内省操作非常的繁琐，所以所以Apache开发了一套简单、易用的API来操作Bean的属性——BeanUtils工具包。
//      　　BeanUtils工具包：下载：http://commons.apache.org/beanutils/　注意：应用的时候还需要一个logging包 http://commons.apache.org/logging/
//      　　使用BeanUtils工具包完成上面的测试代码:
} 
