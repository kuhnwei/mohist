package mohist.examples.java.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExampleAnnotation {
    String name() default "Base-Annotation";
    String value();
}
