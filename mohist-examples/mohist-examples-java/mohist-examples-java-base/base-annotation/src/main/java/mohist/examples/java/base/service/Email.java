package mohist.examples.java.base.service;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
public class Email implements Message {
    @Override
    public void print(String str) {
        System.out.println("[ 邮件消息 ] 内容：" + str);
    }
}
