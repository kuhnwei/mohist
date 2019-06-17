package mohist.examples.java.base;

import mohist.examples.java.base.annotation.ExampleAnnotation;
import mohist.examples.java.base.factory.MessageFactory;
import mohist.examples.java.base.service.Message;
import org.junit.Test;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
@ExampleAnnotation(value = "Hello World !")
@Deprecated
@SuppressWarnings("serial")
public class AnnotationTest {
    @Test
    public void getAnnotations() {
        Annotation[] annotations = AnnotationTest.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
    }

    @Test
    public void getAnnotation() {
        ExampleAnnotation ea = AnnotationTest.class.getAnnotation(ExampleAnnotation.class);
        System.out.println(ea.name());
        System.out.println(ea.value());
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
}
