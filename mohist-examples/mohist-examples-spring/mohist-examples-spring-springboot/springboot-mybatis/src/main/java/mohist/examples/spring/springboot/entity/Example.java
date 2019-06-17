package mohist.examples.spring.springboot.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Data
@ToString
public class Example implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Boolean deleted;
}
