package mohist.examples.spring.springboot.web;

import mohist.examples.spring.springboot.jms.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@RestController
public class PublisherController {

    private Publisher publisher;

    @Autowired
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping(value = "test")
    public String test() {
        return "ok";
    }

    @PostMapping(value = "topicA")
    public ResponseEntity<String> publishA(String message) {
        this.publisher.publish("topic-A", message);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping(value = "queueA")
    public ResponseEntity<String> publishQueueA(String message) {
        this.publisher.publishByQueue("queue-A", message);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping(value = "queueB")
    public ResponseEntity<String> publishQueueB(String message) {
        this.publisher.publishByQueue("queue-B", message);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
