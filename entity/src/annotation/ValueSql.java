package annotation;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


//֧��ֱ�Ӵ����ݿ��ȡ���ݡ�ֻ��
@Retention(RUNTIME)
public @interface ValueSql {
    String sql();
}
