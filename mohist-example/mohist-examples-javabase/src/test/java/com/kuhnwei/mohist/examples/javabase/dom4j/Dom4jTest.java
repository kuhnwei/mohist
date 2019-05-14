package com.kuhnwei.mohist.examples.javabase.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 13:52
 */
public class Dom4jTest {

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
}
