package com.kuhnwei.mohist.examplse.cas.encoder;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/30 19:59
 */
public class Sha256HashTest {

    @Test
    public void encoder() {
        String salt = "mohist-examples-cas-server";
        int hashIterations = 1024;
        String password = "650901";
        System.out.println(new Sha256Hash(password, salt, hashIterations).toHex());
        // 88ee8b221a5284815847b99ccc07ee065ddf344248c02a6a3b1da20124a1b563
        // 88ee8b221a5284815847b99ccc07ee065ddf344248c02a6a3b1da20124a1b563
    }
}
