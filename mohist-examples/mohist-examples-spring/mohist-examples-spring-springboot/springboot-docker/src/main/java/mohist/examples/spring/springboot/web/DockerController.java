package mohist.examples.spring.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/19
 **/
@RestController
public class DockerController {

    @GetMapping("test")
    public String test() {
        return "Hello World .";
    }
}
