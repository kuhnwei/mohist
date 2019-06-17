# Base-Annotation

所有的`Annotation`可以定义在类或者是方法上，那么首先要做的就是获取这些定义的`Annotation`。在Class类里面提供有要给取得全部`Annotation`的方法：`public Annotation[] getAnnotations();`，这个方法返回的是`java.lang.annotation.Annotation`接口对象数组。

***范例：**`getAnnotations()`

```java
@Deprecated
@SuppressWarnings("serial")
public class GetAnnotationsTest {
    @Test
    public void getAnnotations() {
        Annotation[] annotations = GetAnnotationsTest.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
    }
}

//[@java.lang.Deprecated()]
```

在`GetAnnotationsTest.class`上使用了两个注解`@Deprecated`、`@SuppressWarnings("serial")`，但在使用`getAnnotations()`获取所有注解时，只获取到了`@Deprecated`。这是因为在整个`Java`设计过程之中，针对于`Annotation`的作用范围是有定义的，只有`@Deprecated`是在程序运行的时候起作用。如果要想知道`Annotation`的全部范围，那么必须认识枚举类：`java.lang.annotation.RetentionPoliicy`在该类定义了三种`Annotation`的范围：

- CLASS：保存在类之中；
- RUNTIME：程序运行时起作用；
- SOURCE：在源代码之中起作用。

如果我们想要在程序运行时使用，则使用`RUNTIME`类型。

***范例：使用`@interface`自定义`Annotation`**

```java
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExampleAnnotation {
    String name() default "Base-Annotation";
    String value();
}
```

```java
@ExampleAnnotation(value = "Hello World !")
@Deprecated
@SuppressWarnings("serial")
public class AnnotationTest {
    @Test
    public void getAnnotations() {
        Annotation[] annotations = AnnotationTest.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
    }
}

//[@mohist.examples.java.base.ExampleAnnotation(name=Base-Annotation, value=Hello World !), @java.lang.Deprecated()]
```

不过以上的代码只取得了`Annotation`，但在`Annotation`使用的时候设置了内容。在自定义的`@ExampleAnnotation`中有两个属性：`name`、`value`，可通过`public <A extends Annotation> A getAnnotation(Class<A> annotationClass)`方法获取指定注解的对象，通过对象获取属性的内容。

***范例：获取注解的属性内容**

```java
@ExampleAnnotation(value = "Hello World !")
@Deprecated
@SuppressWarnings("serial")
public class AnnotationTest {
    @Test
    public void getAnnotation() {
        ExampleAnnotation ea = AnnotationTest.class.getAnnotation(ExampleAnnotation.class);
        System.out.println(ea.name());
        System.out.println(ea.value());
    }
}

//Base-Annotation
//Hello World !
```

***范例：通过`Annotation`开发工厂设计模式**

```java
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FactoryClass {
    /**
     * 类名称（全称，包括包名。如：java.lang.String）
     * @return
     */
    String className();
}
```

```java
public interface Message {
    void print(String str);
}

public class News implements Message {
    @Override
    public void print(String str) {
        System.out.println("[ 新闻消息 ] 内容：" + str);
    }
}

public class Email implements Message {
    @Override
    public void print(String str) {
        System.out.println("[ 邮件消息 ] 内容：" + str);
    }
}
```

```java
@FactoryClass(className = "mohist.examples.java.base.service.News")
public class MessageFactory {
    public static Message getInstance() throws Exception {
        FactoryClass annotation = MessageFactory.class.getAnnotation(FactoryClass.class);
        String className = annotation.className();
        Class<?> clazz = Class.forName(className);
        return (Message) clazz.newInstance();
    }
}
```

```java
public class AnnotationTest {
    @Test
    public void factory() {
        try {
            Message message = MessageFactory.getInstance();
            message.print("明天开始春节假期！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//[ 新闻消息 ] 内容：明天开始春节假期！
```

**总结：**`Annotation`是`Java`中非常好用的一个机制之一，利用`Annotation`编写的代码非常简洁。就好比`Spring Boot`框架一样，提供了各种各样的`Annotation`注解，方便程序的开发。
