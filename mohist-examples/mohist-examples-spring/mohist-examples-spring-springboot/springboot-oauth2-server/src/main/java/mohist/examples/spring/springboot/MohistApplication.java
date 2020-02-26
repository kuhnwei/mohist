package mohist.examples.spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2020/2/26
 **/
@EntityScan(basePackages = "mohist.examples.spring.springboot.domain.entity")
@SpringBootApplication
public class MohistApplication {
    public static void main(String[] args) {
        SpringApplication.run(MohistApplication.class, args);
    }
}
