package mohist.examples.java.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FactoryClass {
    /**
     * 类名称（全称，包括包名。如：mohist.examples.java.base.annotation.FactoryClass）
     * @return
     */
    String className();
}
