package mohist.examples.spring.springboot.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@Data
@ToString
public class Mqtt {
    private String[] serverUris;
    private String username;
    private String password;
}
