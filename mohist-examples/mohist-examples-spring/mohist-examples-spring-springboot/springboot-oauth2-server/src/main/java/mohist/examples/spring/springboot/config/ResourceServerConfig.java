package mohist.examples.spring.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2020/2/26
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers(
                        "/login",
                        "/logout",
                        "/oauth/authorize",
                        "/oauth/confirm_access"
                )
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }
}
