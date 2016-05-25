package proxy.test;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;


public class BeanUtilTest {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        try {
            BeanUtils.setProperty(userInfo, "userName", "peida");
            System.out.println("set userName:" + userInfo.getUserName());
            System.out.println("get userName:" + BeanUtils.getProperty(userInfo, "userName"));

            BeanUtils.setProperty(userInfo, "age", 18);
            System.out.println("set age:" + userInfo.getAge());
            System.out.println("get age:" + BeanUtils.getProperty(userInfo, "age"));

            System.out.println("get userName type:" + BeanUtils.getProperty(userInfo, "userName").getClass().getName());
            System.out.println("get age type:" + BeanUtils.getProperty(userInfo, "age").getClass().getName()); //��ô���ַ���

            PropertyUtils.setProperty(userInfo, "age", 8);
            System.out.println(PropertyUtils.getProperty(userInfo, "age"));
            System.out.println(PropertyUtils.getProperty(userInfo, "age").getClass().getName());//Integer            
            PropertyUtils.setProperty(userInfo, "age", "8");
            
            
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}



//��1.������Ե�ֵ�����磬BeanUtils.getProperty(userInfo,"userName")�������ַ���
//����2.�������Ե�ֵ�����磬BeanUtils.setProperty(userInfo,"age",8)���������ַ�������������Զ���װ��
//      �������Ե�ֵ���ַ�������õ�ֵҲ���ַ��������ǻ������͡�������3.BeanUtils���ص㣺
//��������1). �Ի����������͵����ԵĲ�������WEB������ʹ���У�¼�����ʾʱ��ֵ�ᱻת�����ַ��������ײ������õ��ǻ������ͣ���Щ����ת��������BeanUtils�Զ���ɡ�
//��������2��. �������������͵����ԵĲ��������������б����ж��󣬲�����null�����磬private Date birthday=new Date();���������Ƕ�������Զ����������������磬BeanUtils.setProperty(userInfo,"birthday.time",111111);������
//3.PropertyUtils���BeanUtils��ͬ���ڣ�����getProperty��setProperty����ʱ��û������ת����ʹ�����Ե�ԭ�����ͻ��߰�װ�ࡣ����age���Ե�����������int�����Է���PropertyUtils.setProperty(userInfo, "age", "8")�ᱬ���������Ͳ�ƥ�䣬�޷���ֵ�������ԡ�
