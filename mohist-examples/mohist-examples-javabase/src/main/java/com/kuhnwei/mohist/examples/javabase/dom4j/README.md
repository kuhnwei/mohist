# DOM4J工具
将DOM可以修改节点与SAX读取快速两个优点结合起来那么就可以形成最早的JDOM开发工具，可是JDOM只是简单的将两种模式融合在一起，而DOM4J对数据的保存做了一些优化。
### 1. 使用DOM4J进行XML文件的创建
 1. 创建Document对象：通过`org.dom4j.DocumentHelper`
 - 创建文档方法：`public static Document createDocument()`
 2. 操作元素：通过`org.dom4j.Document`
 - 增加一个指定的元素，同时配置好子节点：`public Element addElement(String name)`
 - 删除指定元素：`public boolean remove(Element element)`
 - 在Element类之中增加属性：`public Element addAttribute(QName qName, String value)`
 3. 输出XML文档：`org.dom4j.io.OutputFormat`
 - 创建输出：`public static OutputFormat createPrettyPrint()`
 - 设置编码：`public void setEncoding(String encoding)`
 4. 实现IO输出文档：`org.dom4j.io.XMLWriter` 
 - 构造方法：`public XMLWriter(OutputStream out, OutputFormat format) throws UnsupportedEncodingException`
 - 输出数据：`public void write(Document doc) throws IOException`

####范例：使用DOM4J实现内容的输出

```
    @Test
    public void write() {
        Integer[] ids = new Integer[] {1, 2};
        String[] contents = new String[] {"content_1", "content_2"};

        Document document = DocumentHelper.createDocument();
        Element examples = document.addElement("examples");
        for (int i = 0; i < ids.length; i ++) {
            Element example = examples.addElement("example");
            example.addAttribute("id", ids[i].toString());
            Element content = example.addElement("content");
            content.setText(contents[i]);
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream(new File("examples.xml")), format);
            writer.write(document);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```
如果以后牵扯到由Java程序输出XML文件的话永恒都使用DOM4J完成。
### 2. 使用DOM4J读取XML文件 
1. DOM4J是DOM与SAX的结合体，而SAX更加适合读取，所以在DOM4J之中就有了一个公共的SAX解析器的实现，首先要实例化SAX解析器的对象：`org.dom4j.io.SAXReader`
 - 构造方法：`public SAXReader()`
 - 读取数据：`public Document read(File file) throws DocumentException`
2. 通过Document进行读取：
 - 取得根元素：`public Element getRootElement()`
3. 通过Element进行内容的取得：
 - 直接取得一组的子节点：`public Iterator elementIterator()`
 - 取得指定元素的一组节点：`public Iterator elementIterator(String name)`
 - 取得定义的属性：`public String attributeValue(String name)`

####范例：使用DOM4J读取XML文件

```
    @Test
    public void read() {
        File file = new File("examples.xml");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()) {
                Element ele = iter.next();
                System.out.println(ele.attributeValue("id"));
                System.out.println(ele.elementText("content"));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
```
虽然DOM4J给与非常方便的XML读取和输出的支持，但是对于XML的解析操作，在工作里面很少有去使用，关键在于输出上，而节气操作往往还是会用DOM解析。
