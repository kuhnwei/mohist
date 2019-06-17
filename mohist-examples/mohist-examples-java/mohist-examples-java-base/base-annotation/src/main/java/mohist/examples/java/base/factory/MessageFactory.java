package mohist.examples.java.base.factory;

import mohist.examples.java.base.annotation.FactoryClass;
import mohist.examples.java.base.service.Message;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
@FactoryClass(className = "mohist.examples.java.base.service.News")
public class MessageFactory {
    public static Message getInstance() throws Exception {
        FactoryClass anno = MessageFactory.class.getAnnotation(FactoryClass.class);
        String className = anno.className();
        Class<?> clazz = Class.forName(className);
        return (Message) clazz.newInstance();
    }
}
