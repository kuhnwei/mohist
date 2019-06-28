package mohist.examples.spring.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/28
 **/
@RestController
public class DockerController {
    @GetMapping("/")
    public String index() {
        return "Hello Docker!";
    }
}
