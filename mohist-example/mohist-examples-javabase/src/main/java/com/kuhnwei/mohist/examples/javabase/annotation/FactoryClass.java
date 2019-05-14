package com.kuhnwei.mohist.examples.javabase.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 范例
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:22
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FactoryClass {

    /**
     * 需要设置class包.类名称
     * @return class包.类名称
     */
    String className();

}
