package mohist.examples.spring.springboot.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/7/12
 **/
@Data
@Entity(name = "oauth_client_details")
@ToString
public class OauthClientDetails implements Serializable {

    @Id
    private String clientId;
    @Basic
    @Column
    private String clientSecret;
    @Basic
    @Column
    private String resourceIds;
    @Basic
    @Column
    private String scope;
    @Basic
    @Column
    private String authorizedGrantTypes;
    @Basic
    @Column
    private String webServerRedirectUri;
    @Basic
    @Column
    private String authorities;
    @Basic
    @Column
    private Integer accessTokenValidity;
    @Basic
    @Column
    private Integer refreshTokenValidity;
    @Basic
    @Column
    private String additionalInformation;
    @Basic
    @Column
    private Boolean autoapprove;

}
