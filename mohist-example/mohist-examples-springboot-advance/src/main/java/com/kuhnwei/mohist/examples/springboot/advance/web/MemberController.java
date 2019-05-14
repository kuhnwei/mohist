package com.kuhnwei.mohist.examples.springboot.advance.web;

import com.kuhnwei.mohist.examples.springboot.advance.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Iterator;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 21:02
 */
@Controller
public class MemberController extends AbstracBaseController {

    @RequestMapping(value = "/goto_add", method = RequestMethod.GET)
    public String gotoAdd() {
        return "member/member_add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@Valid Member vo, BindingResult result) {
        // 现在表示执行的验证出现错误
        if (result.hasErrors()) {
            // 获取全部错误信息
            Iterator<ObjectError> iterator = result.getAllErrors().iterator();
            while(iterator.hasNext()) {
                // 取出每一个错误
                ObjectError error = iterator.next();
                System.out.println("【错误信息】code = " + error.getCode() + ", message = " + error.getDefaultMessage());
            }
            return result.getAllErrors();
        } else {
            return vo;
        }
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get() {
        System.out.println("除法计算：" + (10 / 0));
        return "hello world";
    }

}
