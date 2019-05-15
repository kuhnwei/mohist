package com.kuhnwei.mohist.examples.javabase.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:13
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExampleAnnotation {
    String name() default "ExampleAnnotaion";
    String value();
}
