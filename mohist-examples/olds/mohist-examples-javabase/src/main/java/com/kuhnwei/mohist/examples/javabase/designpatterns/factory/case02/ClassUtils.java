package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case02;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:40
 */
public class ClassUtils {
    /**
     * 给一个借口，返回这个接口的所有实现类
     * @param c
     * @return
     */
    public static List<Class> getAllClassByInterface(Class c) {
        List<Class> returnClassList = new ArrayList<>();
        if (c.isInterface()) {
            // 获得接口的包名
            String packageName = c.getPackage().getName();
            try {
                List<Class> allClass = getClasses(packageName);
                // 判断是否是同一个接口
                if (allClass != null && allClass.size() != 0) {
                    for (Class o : allClass) {
                        if (c.isAssignableFrom(o)) {
                            if (!c.equals(o)) {
                                returnClassList.add(o);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnClassList;
    }

    private static List<Class> getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File direcotry : dirs) {
            classes.addAll(findClasses(direcotry, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File direcotry, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!direcotry.exists()) {
            return classes;
        }
        File[] files = direcotry.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
