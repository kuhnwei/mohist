package com.kuhnwei.mohist.examples.cas.util.encoder;

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
        // 加密所用的盐
        String salt = "mohist-examples-cas-server";
        // 加密次数
        int hashIterations = 1024;
        passwrod = new Sha256Hash(passwrod, salt, hashIterations).toHex();
        return passwrod;
    }
}
