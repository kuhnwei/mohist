# mohist-examplse-cas-server

## 取消HTTPS，使用HTTP的修改

1. 修改services/HTTPSandIMAPS-10000001.json文件添加http：`"serviceId" : "^(http|https|imaps)://.*"`；
2. 修改deployerConfigContext.xml文件：
    ```
    <!-- 在p:httpClient-ref="supportsTrustStoreSslSocketFactoryHttpClient"后增加p:requireSecure="false" -->
    <bean id="proxyAuthenticationHandler"
          class="org.jasig.cas.authentication.handler.support.HttpBasedServiceCredentialsAuthenticationHandler"
          p:httpClient-ref="supportsTrustStoreSslSocketFactoryHttpClient" 
          p:requireSecure="false"/>
    ```
3. 修改spring-configuration/ticketGrantingTicketCookieGenerator.xml文件：
    ```
    <!-- 将p:cookieSecure="true"修改为p:cookieSecure="false" -->
    <bean id="ticketGrantingTicketCookieGenerator" class="org.jasig.cas.web.support.CookieRetrievingCookieGenerator"
          c:casCookieValueManager-ref="cookieValueManager"
          p:cookieSecure="false"
          p:cookieMaxAge="-1"
          p:cookieName="TGC"
          p:cookiePath=""/>
    ```
4. 修改spring-configuration/warnCookieGenerator.xml文件：
    ```
    <!-- 将p:cookieSecure="true"修改为p:cookieSecure="false" -->
    <bean id="warnCookieGenerator" class="org.jasig.cas.web.support.CookieRetrievingCookieGenerator"
          p:cookieSecure="false"
          p:cookieMaxAge="-1"
          p:cookieName="CASPRIVACY"
          p:cookiePath=""/>
    ```
5. 因以上修改导致登录页有错误提示，所以对/WEB-INF/view/jsp/default/ui/casLoginView.jsp做以下修改：
    ```
    <%--
    删除提示
    <c:if test="${not pageContext.request.secure}">
        <div id="msg" class="errors">
            <h2><spring:message code="screen.nonsecure.title" /></h2>
            <p><spring:message code="screen.nonsecure.message" /></p>
        </div>
    </c:if>
    --%>
    ```
## 因`https://cdn.rawgit.com/cowboy/javascript-debug/master/ba-debug.min.js`请求异常，所以做如下修改
修改/WEB-INF/view/jsp/default/ui/includes/bottom.jsp,注释掉以下代码
```
<%--
<script src="https://cdnjs.cloudflare.com/ajax/libs/headjs/1.0.3/head.min.js"></script>
<spring:theme code="cas.javascript.file" var="casJavascriptFile" text="" />
<script type="text/javascript" src="<c:url value="${casJavascriptFile}" />"></script>
--%>
```

## 1、使用MySQL验证用户名和密码
1. 引入相关依赖包
    ```
    <dependency>
        <groupId>org.jasig.cas</groupId>
        <artifactId>cas-server-support-jdbc</artifactId>
        <version>4.1.7</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.38</version>
    </dependency>
    ```
2. 修改配置文件：deployerConfigContext.xml
    - 配置数据库连接池
    ```
    <!-- 配置使用的数据库连接池类型，同时设置卸载方法为close() -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
        <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/mohist_examples"/>
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="user" value="root"/>
        <property name="password" value=""/>
        <property name="maxPoolSize" value="1"/>
        <property name="minPoolSize" value="1"/>
        <property name="initialPoolSize" value="1"/>
        <property name="maxIdleTime" value="20"/>
    </bean>
    ```
    - 增加数据库检测认证器
    ```
    <!-- 增加数据库检测认证器 -->
    <bean id="dbAuthHandler" class="org.jasig.cas.adaptors.jdbc.QueryDatabaseAuthenticationHandler">
        <property name="dataSource" ref="dataSource"/>
        <property name="sql" value="SELECT password FROM t_mohist_user WHERE username=? AND locked=0"/>
    </bean>
    ```
    - 修改认证授权
    ```
    <!-- 原始配置 -->
    <!--<entry key-ref="primaryAuthenticationHandler" value-ref="primaryPrincipalResolver" />-->
    <!-- 新配置项 -->
    <entry key-ref="dbAuthHandler" value-ref="primaryPrincipalResolver"/>
    ```
## 2、在CAS中使用Sha256Hash加密处理
1. 需要为CAS做一个新的密码加密器
CAS的密码加密器有自己的一个专属实现类，而这个实现类在“cas-server-core-api-4.1.7.jar”包里面；
    ```
    package com.kuhnwei.mohist.examplse.cas.util.encoder;
    import org.apache.shiro.crypto.hash.Sha256Hash;
    import org.jasig.cas.authentication.handler.PasswordEncoder;
    /**
     * 自定义的密码加密器
     * @author Kuhn Wei, email@kuhnwei.com
     * @version 2018/3/30 19:46
     */
    public class CustomPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(String passwrod) {
            String salt = "mohist-examples-cas-server";
            passwrod = new Sha256Hash(passwrod, salt, 1024).toHex();
            return passwrod;
        }
    }
    ```
2. 编辑deployerConfigContext.xml配置文件，引入自定义的加密器类操作；
    - 先定义密码加密处理的bean生命
    ```
    <bean id="passwordEncoder" class="CustomPasswordEncoder"/>
    ```
    - 修改定义好的数据库认证配置
    ```
    <bean id="dbAuthHandler" class="org.jasig.cas.adaptors.jdbc.QueryDatabaseAuthenticationHandler">
        <property name="dataSource" ref="dataSource"/>
        <property name="sql" value="SELECT password FROM t_mohist_user WHERE username=? AND locked=0"/>
        <property name="passwordEncoder" ref="passwordEncoder"/>
    </bean>
    ```
## 3、配置RememberMe
1. 修改“deployerConfigContext.xml”文件
    ```
    <bean id="authenticationManager" class="org.jasig.cas.authentication.PolicyBasedAuthenticationManager">
        <constructor-arg>
            <map>
                <entry key-ref="proxyAuthenticationHandler" value-ref="proxyPrincipalResolver" />
                <entry key-ref="dbAuthHandler" value-ref="primaryPrincipalResolver"/>
            </map>
        </constructor-arg>
        <property name="authenticationPolicy">
            <bean class="org.jasig.cas.authentication.AnyAuthenticationPolicy" />
        </property>
        <!-- 添加以下内容 -->
        <property name="authenticationMetaDataPopulators">
            <list>
                <bean class="org.jasig.cas.authentication.principal.RememberMeAuthenticationMetaDataPopulator"/>
            </list>
        </property>
    </bean>
    ```
2. 修改“webflow\login\login-webflow.xml”文件
    ```
    <!-- 原配置项 -->
    <var name="credential" class="org.jasig.cas.authentication.UsernamePasswordCredential"/>
    <!-- 新配置项 -->
    <var name="credential" class="org.jasig.cas.authentication.RememberMeUsernamePasswordCredential"/>
    
    <view-state id="viewLoginForm" view="casLoginView" model="credential">
        <binder>
            <binding property="username" required="true"/>
            <binding property="password" required="true"/>
            <!-- 添加rememberMe属性 -->
            <binding property="rememberMe"/>
        </binder>
        <on-entry>
            <set name="viewScope.commandName" value="'credential'"/>
        </on-entry>
        <transition on="submit" bind="true" validate="true" to="realSubmit"/>
    </view-state>
    ```
3. 修改“spring-configuration\ticketExpirationPolicies.xml”文件
    ```
    <!-- 原配置项 -->
    <bean id="grantingTicketExpirationPolicy" class="org.jasig.cas.ticket.support.TicketGrantingTicketExpirationPolicy"
          c:maxTimeToLive="${tgt.maxTimeToLiveInSeconds:28800}" c:timeToKill="${tgt.timeToKillInSeconds:7200}" c:timeUnit-ref="SECONDS" />
    <!-- 新配置项 -->
    <bean id="grantingTicketExpirationPolicy" class="org.jasig.cas.ticket.support.RememberMeDelegatingExpirationPolicy">
        <property name="sessionExpirationPolicy" ref="timeoutExpirationPolicy"/>
        <property name="rememberMeExpirationPolicy" ref="timeoutExpirationPolicy"/>
    </bean>
    <bean id="timeoutExpirationPolicy" class="org.jasig.cas.ticket.support.TimeoutExpirationPolicy">
        <constructor-arg index="0" value="100000"/>
    </bean>
    ```
4. 修改“spring-configuration\ticketGrantingTicketCookieGenerator.xml”文件
    ```
    <!-- 添加p:rememberMeMaxAge="100000" -->
    <bean id="ticketGrantingTicketCookieGenerator" class="org.jasig.cas.web.support.CookieRetrievingCookieGenerator"
          c:casCookieValueManager-ref="cookieValueManager"
          p:cookieSecure="false"
          p:cookieMaxAge="-1"
          p:cookieName="TGC"
          p:cookiePath=""
          p:rememberMeMaxAge="100000"/>
    ```
5. 修改“view/jsp/default/ui/casLoginView.jsp”文件
    ```
    <!-- 在登录页面添加rememberMe的checkbox控件 -->
    <section class="row">
        <input type="checkbox" name="rememberMe" id="rememberMe" value="true">
        <label for="rememberMe">
            <spring:message code="screen.rememberme.checkbox.title"/>
        </label>
    </section>
    ```
## 配置登录验证码
1. 引入相关jar包;
    ```
    <dependency>
        <groupId>com.google.code</groupId>
        <artifactId>kaptcha</artifactId>
        <version>2.3</version>
    </dependency>
    <dependency>
        <groupId>com.jhlabs</groupId>
        <artifactId>filters</artifactId>
        <version>2.0.235</version>
    </dependency>
    ```
2. 在web.xml文件配置KaptchaServlet
    ```
    <servlet>
        <servlet-name>KaptchaServlet</servlet-name>
        <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class>
        <init-param>
            <!-- 是否显示边框 -->
            <param-name>kaptcha.border</param-name>
            <param-value>no</param-value>
        </init-param>
        <init-param>
            <!-- 边框颜色 -->
            <param-name>kaptcha.border.color</param-name>
            <param-value>105,179,90</param-value>
        </init-param>
        <init-param>
            <!-- 字体颜色 -->
            <param-name>kaptcha.textproducer.font.color</param-name>
            <param-value>red</param-value>
        </init-param>
        <init-param>
            <!-- 图片宽度 -->
            <param-name>kaptcha.image.width</param-name>
            <param-value>100</param-value>
        </init-param>
        <init-param>
            <!-- 图片高度 -->
            <param-name>kaptcha.image.height</param-name>
            <param-value>40</param-value>
        </init-param>
        <init-param>
            <!-- 文字尺寸 -->
            <param-name>kaptcha.textproducer.font.size</param-name>
            <param-value>35</param-value>
        </init-param>
        <init-param>
            <!-- 验证码保存在session的属性名 -->
            <param-name>kaptcha.session.key</param-name>
            <param-value>rand</param-value>
        </init-param>
        <init-param>
            <!-- 字符间距 -->
            <param-name>kaptcha.textproducer.char.space</param-name>
            <param-value>2</param-value>
        </init-param>
        <init-param>
            <!-- 验证码长度 -->
            <param-name>kaptcha.textproducer.char.length</param-name>
            <param-value>5</param-value>
        </init-param>
        <init-param>
            <!-- 字体类型 -->
            <param-name>kaptcha.textproducer.font.names</param-name>
            <param-value>宋体,楷体,微软雅黑</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>KaptchaServlet</servlet-name>
        <url-pattern>/captcha.jpg</url-pattern>
    </servlet-mapping>
    ```
3. 创建用于处理验证码的相关Java类
    `com.kuhnwei.mohist.examples.cas.authentication.CaptchaRememberMeUsernamePasswordCredential `类，并继承`org.jasig.cas.authentication.RememberMeUsernamePasswordCredential`类；
    `com.kuhnwei.mohist.examples.cas.authentication.NullCaptchaException`继承至`org.jasig.cas.authentication.RootCasException`;
    `com.kuhnwei.mohist.examples.cas.authentication.BadCaptchaException`继承至`org.jasig.cas.authentication.RootCasException`;
    `com.kuhnwei.mohist.examples.cas.web.flow.CaptchaAuthenticationViaFormAction `继承至`org.jasig.cas.web.flow.AuthenticationViaFormAction`;
4. 修改“cas-servlet.xml”文件
    ```
    <!--原始内容-->
    <bean id="authenticationViaFormAction" class="org.jasig.cas.web.flow.AuthenticationViaFormAction"
          p:centralAuthenticationService-ref="centralAuthenticationService"
          p:warnCookieGenerator-ref="warnCookieGenerator"/>
    <!--更新后的内容-->
    <bean id="authenticationViaFormAction"
          class="com.kuhnwei.mohist.examples.cas.web.flow.CaptchaAuthenticationViaFormAction"
          p:centralAuthenticationService-ref="centralAuthenticationService"
          p:warnCookieGenerator-ref="warnCookieGenerator"/>
    ```
5. 修改“webflow/login/login-webflow.xml”文件
    ```
    <!--原始内容-->
    <var name="credential" class="org.jasig.cas.authentication.RememberMeUsernamePasswordCredential"/>
    <!--更新后的内容-->
    <var name="credential" class="com.kuhnwei.mohist.examples.cas.authentication.CaptchaRememberMeUsernamePasswordCredential"/>
    
    <!--定义验证码的输入验证处理-->
    <action-state id="captchaSubmit">
        <evaluate expression="authenticationViaFormAction.validatorCaptcha(flowRequestContext, flowScope.credential, messageContext)"/>
        <transition on="error" to="generateLoginTicket"/>
        <transition on="success" to="realSubmit"/>
    </action-state>
    
    <view-state id="viewLoginForm" view="casLoginView" model="credential">
        <binder>
            <binding property="username" required="true"/>
            <binding property="password" required="true"/>
            <binding property="captcha" required="true"/>
            <binding property="rememberMe"/>
        </binder>
        <on-entry>
            <set name="viewScope.commandName" value="'credential'"/>
        </on-entry>
        <transition on="submit" bind="true" validate="true" to="captchaSubmit"/>
    </view-state>
    ```
6. 修改资源文件“messages*.properties”,添加验证码相关信息，用于国际化处理
    ```
    screen.welcome.label.captcha=验证码
    screen.welcome.label.captcha.accesskey=c
    captcha.required=请输入验证码！
    error.authentication.captcha.bad=验证码输入错误！
    ```
7. 修改cas的登录页面“view/jsp/default/ui/casLoginView.jsp”，在表单里添加验证码
    ```
    <section class="row">
        <label for="captcha">
            <spring:message code="screen.welcome.label.captcha"/>
        </label>
        <spring:message code="screen.welcome.label.captcha.accesskey" var="captchaAccesskey"/>
        <form:input path="captcha" cssClass="required" cssErrorClass="error" id="captcha" size="5" tabindex="3" accesskey="${captchaAccesskey}" autocomplete="off" htmlEscape="true"/>
        <img src="captcha.jpg" onclick="this.src='captcha.jpg?' + Math.random()">
    </section>
    ```

