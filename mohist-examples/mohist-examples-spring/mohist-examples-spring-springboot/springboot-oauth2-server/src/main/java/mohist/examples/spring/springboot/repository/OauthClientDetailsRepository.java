package mohist.examples.spring.springboot.repository;

import mohist.examples.spring.springboot.domain.entity.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/7/12
 **/
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, String> {
}
