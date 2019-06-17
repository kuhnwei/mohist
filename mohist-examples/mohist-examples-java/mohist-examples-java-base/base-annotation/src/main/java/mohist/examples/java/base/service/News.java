package mohist.examples.java.base.service;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
public class News implements Message {
    @Override
    public void print(String str) {
        System.out.println("[ 新闻消息 ] 内容：" + str);
    }
}
