package mohist.examples.spring.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MohistApplication.class)
public class SpringBootBaseTest {

    @Test
    public void test() {}
}
