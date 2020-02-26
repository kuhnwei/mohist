package mohist.examples.spring.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2020/2/26
 **/
@RestController
public class TestController {

    @GetMapping("test")
    public String test() {
        return "Hello World ...";
    }
}
