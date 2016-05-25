package test;

import entity.Employees;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

public class BeanInfoUtil {   
    
    /**
     * ��PropertyDescriptor��:

����PropertyDescriptor���ʾJavaBean��ͨ���洢������һ�����ԡ���Ҫ������
    ����1. getPropertyType()��������Ե�Class����;
    ����2. getReadMethod()��������ڶ�ȡ����ֵ�ķ�����getWriteMethod()���������д������ֵ�ķ���;
    ����3. hashCode()����ȡ����Ĺ�ϣֵ;
    ����4. setReadMethod(Method readMethod)���������ڶ�ȡ����ֵ�ķ���;
    ����5. setWriteMethod(Method writeMethod)����������д������ֵ�ķ�����
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
    
    
/**Introspector��:

    ������JavaBean�е����Է�װ�������в������ڳ����һ���൱��JavaBean���������ǵ���Introspector.getBeanInfo()�������õ���BeanInfo�����װ�˰�����൱��JavaBean���Ľ����Ϣ�������Ե���Ϣ��

    ����getPropertyDescriptors()��������Ե����������Բ��ñ���BeanInfo�ķ����������ҡ�����������ԡ�����������£�*/
    
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
      
      // ͨ����������ıȽϿ��Կ�����������Ҫ���PropertyDescriptor��ֻ�Ƿ�ʽ��һ����ǰ��ͨ����������ֱ�ӻ�ã�������Ҫ����������ʹ��PropertyDescriptor����ӷ��㡣
      
      
//      �������ɿ�������ʡ�����ǳ��ķ�������������Apache������һ�׼򵥡����õ�API������Bean�����ԡ���BeanUtils���߰���
//      ����BeanUtils���߰������أ�http://commons.apache.org/beanutils/��ע�⣺Ӧ�õ�ʱ����Ҫһ��logging�� http://commons.apache.org/logging/
//      ����ʹ��BeanUtils���߰��������Ĳ��Դ���:
} 
