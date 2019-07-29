package mohist.examples;

import io.vertx.core.AbstractVerticle;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/7/29
 **/
public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }
}
