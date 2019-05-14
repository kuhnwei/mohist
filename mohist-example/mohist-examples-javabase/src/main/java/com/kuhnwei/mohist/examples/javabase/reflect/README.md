#反射的基本操作原理
在整个反射的开发模式之中，有一个最为重要的组成那么就是java.lang.Class&lt;T&gt;类。但实例化对象，有三种方式可以完成：
- Object类之中存在有一个getClass()方法：public final Class&lt;?&gt; getClass();此方法不能被子类所覆写，而且所有类的实例化对象都可以调用；
- 利用"包.类.class"的形式实例化Class类对象；例如：java.util.Date.class, 在一些开源框架之中会大量使用到，例如：Hibernate、Mybatis
- 利用Class类之中提供的forName()方法：public static Class&lt;?&gt; forName(String className) throws ClassNotFoundException ;主要可以用于工厂类上，设计上JDBC的驱动程序加载使用的就是此方法。
#####范例：验证第一种模式
```
    @Test
    public void _getClass() {
        String str = "Hello World .";
        Class<?> cls = str.getClass();
        System.out.println(cls);
    }
```
以上的方式是不会使用到的，在所有的开发环境里面这种凡是可以使用的几率是很低的。
#####范例：验证第二种模式
```
    @Test
    public void setClass() {
        Class<?> cls = java.lang.String.class;
        System.out.println(cls);
    }
```
此操作方式不需要得到指定操作类的实例化对象，而直接通过类的名称就可以完成了。少了一个对象的空间了。但是以上的方式虽然严谨，那么却属于一个明确得结构，即：定义的时候类必须存在。
#####范例：验证第三种模式
```
    @Test
    public void forName() {
        try {
            Class<?> cls = Class.forName("java.lang.String");
            System.out.println(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
```
那么使用此方式操作的最大特点：程序没有很强的严谨性，操作的类可以不存在，只要你程序不运行，那么就不会出现任何的语法错误，如果要运行，那么就把指定的类设置上。
那么取得Class对象之后，最为直观的操作表现就在于，对象的实例化操作的方式可以发生改变了。在Class类里面提供有一个实例化对象的操作方法：public T newInstance() throws InstantiationException, IllegalAccessException;
 - **InstantiationException**: 没有无参构造，类名错误
 - **IllegalAccessException**：构造方法私有化
#####范例：利用反射实例化对象
```
    @Test
    public void newInstance() {
        try {
            Class<?> cls = Class.forName("Example");
            System.out.println(cls.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // 构造方法私有化
            e.printStackTrace();
        } catch (InstantiationException e) {
            // 没有无参构造方法
            e.printStackTrace();
        }
    }
```
使用"newInstance()"方法只能够调用类中的无参构造方法，这个时候的功能相当于使用new实现对象的实例化操作。

虽然这个时候实现了工厂设计模式，但是本代码却具备一个非常大的缺点（根据子类扩充来决定的）：每当接口扩充新的子类的时候，都需要去修改工厂类，这对于一个代码根本就属于一个灾难性的折磨。最好的编码：一个类写完之后，那么可以适应于所有的情况变化，所以这种严谨性的代码，那么就不适合于实际的编写。
#####范例：利用反射来解决工厂类的设计问题
```
 package cn.vkin.demo;
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

public class MessageFactory {

    public static Message getInstance(String className) {
        try {
            return (Message) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

@Test
public void factory() {
    Message message = MessageFactory.getInstance("com.kuhnwei.javabase.Email");
    message.print("啦啦啦啦 ！！！！");
}
```


