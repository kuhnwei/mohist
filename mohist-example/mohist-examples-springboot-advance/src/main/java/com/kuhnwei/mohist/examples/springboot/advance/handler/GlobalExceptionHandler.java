package com.kuhnwei.mohist.examples.springboot.advance.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 22:47
 */
// 作为一个控制层的切面处理
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 定义错误显示页 error.html
    public static final String DEFAULT_ERROR_VIEW = "error";
    // 所有的异常都是Exception的子类
    @ExceptionHandler(Exception.class)
    public Object defaultErrorHandler(HttpServletRequest request, Exception e) {
        class ErrorInfo {
            private Integer code;
            private String message;
            private String url;
            public Integer getCode() {
                return code;
            }
            public void setCode(Integer code) {
                this.code = code;
            }
            public String getMessage() {
                return message;
            }
            public void setMessage(String message) {
                this.message = message;
            }
            public String getUrl() {
                return url;
            }
            public void setUrl(String url) {
                this.url = url;
            }
        }
        ErrorInfo info = new ErrorInfo();
        // 标记一个错误信息类型
        info.setCode(100);
        info.setMessage(e.getMessage());
        info.setUrl(request.getRequestURL().toString());
        return info;
    }
}
