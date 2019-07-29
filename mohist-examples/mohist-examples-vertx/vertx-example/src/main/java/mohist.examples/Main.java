package mohist.examples;

import io.vertx.core.Vertx;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/7/29
 **/
public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }
}
