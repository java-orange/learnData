package com.bjpowernode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

/**
 * @RequestMapping:
 *    value ： 所有请求地址的公共部分，叫做模块名称
 *    位置： 放在类的上面
 */
@Controller
public class MyController {


    /**
     * 处理器方法返回ModelAndView,实现转发forward
     * 语法： setViewName("forward:视图文件完整路径")
     * forward特点： 不和视图解析器一同使用，就当项目中没有视图解析器
     */
    @RequestMapping(value = "/doForward.do")
    public ModelAndView doSome(){
        //处理some.do请求了。 相当于service调用处理完成了。
        ModelAndView mv  = new ModelAndView();
        mv.addObject("msg","欢迎使用springmvc做web开发");
        mv.addObject("fun","执行的是doSome方法");
        //显示转发
        //mv.setViewName("forward:/WEB-INF/view/show.jsp");

        mv.setViewName("forward:/hello.jsp");
        return mv;
    }


    /**
     * 处理器方法返回ModelAndView,实现重定向redirect
     * 语法：setViewName("redirect:视图完整路径")
     * redirect特点： 不和视图解析器一同使用，就当项目中没有视图解析器
     *
     * 框架对重定向的操作：
     * 1.框架会把Model中的简单类型的数据，转为string使用，作为hello.jsp的get请求参数使用。
     *   目的是在 doRedirect.do 和 hello.jsp 两次请求之间传递数据
     *
     * 2.在目标hello.jsp页面可以使用参数集合对象 ${param}获取请求参数值
     *    ${param.myname}
     *
     * 3.重定向不能访问/WEB-INF资源
     */
    @RequestMapping(value = "/doRedirect.do")
    public ModelAndView doWithRedirect(String name,Integer age){
        //处理some.do请求了。 相当于service调用处理完成了。
        ModelAndView mv  = new ModelAndView();
        //数据放入到 request作用域
        mv.addObject("myname",name);
        mv.addObject("myage",age);
        //重定向
        //mv.setViewName("redirect:/hello.jsp");
        //http://localhost:8080/ch08_forard_redirect/hello.jsp?myname=lisi&myage=22

        //重定向不能访问/WEB-INF资源
        mv.setViewName("redirect:/WEB-INF/view/show.jsp");
        return mv;
    }


}
