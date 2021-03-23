package com.bjpowernode.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @RequestMapping:
 *    value ： 所有请求地址的公共部分，叫做模块名称
 *    位置： 放在类的上面
 */
@Controller
public class MyController {


    @RequestMapping(value = "/some.do")
    public ModelAndView doSome(String name,Integer age)  {
        System.out.println("=====执行MyController中的doSome方法=====");
        //处理some.do请求了。 相当于service调用处理完成了。
        ModelAndView mv  = new ModelAndView();
        mv.addObject("myname",name);
        mv.addObject("myage",age);
        mv.setViewName("show");
        return mv;
    }


}
