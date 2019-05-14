# properties
这个时候虽然解决了工厂类的固化问题，但是有一个新的问题产生了，我的客户端代码还是固定的。客户端必须明确写出要操作的类名称，这样一来代码就会造成新的耦合问题。
最好的设计方案永远不是点对点直达（最快），通过key找到value。如果说一个普通用户，那么它一定不能够去修改程序逻辑代码，但是它可以修改文件的内容，所以此时如果要解决这种新的耦合问题，可以采用属性文件的方式完成，在java.util包中有一个Properties类，它是Hashtable的子类，这个类可以定义"key=value"的文件，在这个类里面提供了以下的两个操作方法：
- 通过输入流读取属性内容：public void  load(InputStream inStream) throws IOException；
- 通过输出流输出属性内容：public void store(OutputStream out, String comments) throws IOException。
#####范例：观察Properties类的使用，保存文件
```
@Test
public void store() {
    Properties properties = new Properties();
    properties.setProperty("message.className", "com.kuhnwei.javabase.News");
    properties.setProperty("author", "KuhnWei");
    try {
        // 如果不写绝对路径将会按照相对路径保存到项目环境根目录下new File("info.properties")
        properties.store(new FileOutputStream(new File("info.properties")), "This is Properties File");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
只要是属性文件其后缀必须为"*.properties"。
通过Properties进行的内容输出之后发现其结构都按照了"key = value"的形式保存，所有的中文自动转换编码(如果在MyEclipse之中自己定义*.properties文件，会帮助用户自己转换编码)。
#####范例：观察Properties类的使用，读取文件
```
@Test
 public void load() {
     Properties properties = new Properties();
     try {
         properties.load(new FileInputStream(new File("info.properties")));
         System.out.println(properties.getProperty("author"));
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
```
通过程序可以发现，Properties类可以直接具备IO流的程序操作能力。

#####范例：利用java.util.ResourceBundle类读取
```
@Test
public void getBundle() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("info");
    System.out.println(resourceBundle.getString("author"));
}
```
如果读取CLASSPATH中的资源文件，还是使用ResourceBundle要比直接使用Properties简单，但是要生成*.properties文件的时候还是使用Properties类比较好。
所以在所有日后的开发之中，资源文件的读取都会使用ResourceBundle类，而且以上的代码可以总结出一个特点：只要是定义的时候存在有“包.类”字符串，那么就表示使用了反射处理。

