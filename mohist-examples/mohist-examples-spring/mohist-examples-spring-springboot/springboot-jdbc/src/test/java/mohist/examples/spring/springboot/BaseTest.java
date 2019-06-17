package mohist.examples.spring.springboot;

import org.junit.Test;

import java.util.UUID;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
public class BaseTest {

    @Test
    public void test () {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        System.out.println("0ca8c62b-b8f0-4286-9b30-6a3a5d94".length());
        System.out.println("69752e52-ce2f-4a4b-8bb7-398d77b7334e".length());
    }
}
