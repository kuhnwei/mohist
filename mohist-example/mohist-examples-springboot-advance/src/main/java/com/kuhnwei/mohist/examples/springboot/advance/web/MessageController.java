package com.kuhnwei.mohist.examples.springboot.advance.web;

import com.kuhnwei.mohist.examples.springboot.advance.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 16:17
 */
@Controller
public class MessageController extends AbstracBaseController {

    @Resource(name="messageService")
    private MessageService messageService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(String mid, Model model) {
        // request属性传递包装
        model.addAttribute("url", "www.kuhnwei.com");
        model.addAttribute("mid", mid);
        // 此处只返回一个路径，该路径没有设置后缀，后缀默认是*.html
        return "message/message_show";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return this.messageService.info();
    }

    @ResponseBody
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public String echo(String mid) {
        System.out.println("【*** 访问地址 ***】" + super.getMessage("member.add.action"));
        return super.getMessage("welcome.msg", mid);
    }


}
