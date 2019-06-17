package com.kuhnwei.mohist.examples.javabase.properties;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 12:26
 */
public class PropertiesTest {

    @Test
    public void store() {
        Properties properties = new Properties();
        properties.setProperty("message.className", "News");
        properties.setProperty("author", "KuhnWei");
        try {
            // 如果不写绝对路径将会按照相对路径保存到项目环境根目录下new File("info.properties")
            File file = new File("info.properties");
            FileOutputStream os = new FileOutputStream(file);
            properties.store(os, "This is Properties File");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void load() {
        Properties properties = new Properties();
        try {
            File file = new File("info.properties");
            FileInputStream is = new FileInputStream(file);
            properties.load(is);
            is.close();
            System.out.println(properties.getProperty("author"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBundle() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("info");
        System.out.println(resourceBundle.getString("author"));
    }

}
