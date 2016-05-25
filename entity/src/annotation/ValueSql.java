package annotation;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


//支持直接从数据库读取数据。只读
@Retention(RUNTIME)
public @interface ValueSql {
    String sql();
}
