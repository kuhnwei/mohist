package mohist.examples.spring.springboot.config;

import lombok.Data;
import mohist.examples.spring.springboot.domain.Mqtt;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mohist")
public class MohistProperties {
    private Mqtt mqtt;
}

