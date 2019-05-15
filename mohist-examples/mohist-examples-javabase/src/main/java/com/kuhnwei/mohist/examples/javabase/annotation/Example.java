package com.kuhnwei.mohist.examples.javabase.annotation;

import java.io.Serializable;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:08
 */
@SuppressWarnings("serial")
@Deprecated
@ExampleAnnotation(value = "Hello World.")
public class Example implements Serializable {
}
