package annotation;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


//֧��JSP����ѡ��
@Retention(RUNTIME)
public @interface SelectScope {
    String sql() default "";
    String hql() default "";  
    //��һ��ֵΪlabel�������ֵΪvalue,���ΪHQL��ֵ����list<����>
    //:��ʾΪ����,Ĭ��Ϊ����Ķ�Ӧ����ֵ
}
