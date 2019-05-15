#Annotation 操作原理

所有的Annotation可以定义在类或者是方法上，那么首先要做的就是获取这些定义的Annotation。在Class类里面提供有一个取得全部Annotation方法：`public Annotation[] getAnnotation()`, 这个方法返回的是java.lang.annotation.Annotation接口对象数组.

##### 范例：取得全部Anotation
```
    @Test
    public void getAnnotations() {
        Class<?> cls = Example.class;
        Annotation[] annotations = cls.getAnnotations();
        System.out.println(Arrays.toString(annotations));
    }
//输出结果：@java.lang.Deprecated()
```
最终发现只取得了一个Annotation，因为在整个Java设计过程之中，针对于Annotation的作用范围是有定义的，只有“@Deprecated”是在程序运行的时候起作用的Annotation。如果要想知道Annotation的全部范围，那么必须查询一个枚举类：java.lang.annotation.RetentionPoliicy,在这个类里面定义了三种Annotation的范围：
- CLASS: 保存在类之中；
- RUNTIME: 程序运行时起作用；
- SOURCE: 在原代码之中起作用。

如果我们想要编写的程序可以在运行的时候使用，使用RUNTIME类型。下面来顶一个属于自己的Annotation：使用“@interface”定义

```
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExampleAnnotation {
    String name() default "ExampleAnnotaion";
    String value();
}

@SuppressWarnings("serial")
@Deprecated
@ExampleAnnotation(value = "Hello World.")
public class Example implements Serializable {
}

@Test
public void getAnnotations() {
    Class<?> cls = Example.class;
    Annotation[] annotations = cls.getAnnotations();
    System.out.println(Arrays.toString(annotations));
}
```
不过以上的代码只是取得了Annotation，但是不要忘记了，在Annotation使用的时候里面设置了内容，现在有两个参数内容，一个是name、另外一个是val，这个时候只能取得一个指定的Annotation，而后调用里面的方法，在Class类里定义了取得指定Annotation的方法：`public <A extends Annotation> A getAnnotation(Class<A> annotationClass)`。
##### 范例：取得指定的Annotation

```
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExampleAnnotation {
    String name() default "ExampleAnnotaion";
    String value();
}

@SuppressWarnings("serial")
@Deprecated
@ExampleAnnotation(value = "Hello World.")
public class Example implements Serializable {
}

@Test
public void getAnnotation() {
    Class<?> cls = Example.class;
    ExampleAnnotation ea = cls.getAnnotation(ExampleAnnotation.class);
    System.out.println(ea.name());
    System.out.println(ea.value());
}
```
利用Annotation来进行工厂设计模式的修改，在使用的客户端上定义Annotation。
 
##### 范例：利用Annotation开发
```
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FactoryClass {

    /**
     * 需要设置class包.类名称
     * @return class包.类名称
     */
    String className();

}


interface Message {
	public void print(String str) ;
}
class News implements Message {
	public void print(String str) {
		System.out.println("新闻消息。内容：" + str);
	};
}
class Email implements Message {
	@Override
	public void print(String str) {
		System.out.println("邮件消息。内容：" + str);
	}
}

@FactoryClass(className = "com.kuhnwei.javabase.News")
public class MessageFactory {

    public static Message getInstance() throws Exception {
        Class<?> cls = MessageFactory.class;
        FactoryClass factoryClass = cls.getAnnotation(FactoryClass.class);
        String className = factoryClass.className();
        Class<?> msgIns = Class.forName(className);
        return (Message) msgIns.newInstance();
    }

}

@Test
public void factory() {
    try {
        Message message = MessageFactory.getInstance();
        message.print("明天开始春节假期！");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
**总结**：Annotation好用，而且利用Annotation编写的代码非常简洁，但是它的开发太麻烦了，可是在现阶段的开发之中，会出现“程序 + 配置文件”、“程序 + Annotation”共存的状态。

