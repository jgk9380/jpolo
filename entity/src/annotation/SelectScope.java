package annotation;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


//支持JSP界面选择
@Retention(RUNTIME)
public @interface SelectScope {
    String sql() default "";
    String hql() default "";  
    //第一个值为label，后面的值为value,如果为HQL，值返回list<对象>
    //:表示为参数,默认为对象的对应属性值
}
