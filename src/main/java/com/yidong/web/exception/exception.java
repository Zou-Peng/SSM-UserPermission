package com.yidong.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class exception implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        e.printStackTrace();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("failer");
        mv.addObject("errorMsg","系统繁忙，请联系管理员");
        return mv;
    }
}
