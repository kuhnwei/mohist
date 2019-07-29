package mohist.examples;

import io.vertx.core.AbstractVerticle;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/7/29
 **/
public class MyFirstVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World!");
        }).listen(8080);
    }
}
