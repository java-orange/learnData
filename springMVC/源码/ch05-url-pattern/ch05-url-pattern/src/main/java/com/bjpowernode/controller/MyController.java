package com.bjpowernode.controller;

import com.bjpowernode.vo.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @RequestMapping:
 *    value ： 所有请求地址的公共部分，叫做模块名称
 *    位置： 放在类的上面
 */
@Controller
public class MyController {


    @RequestMapping(value = "/some")
    public ModelAndView doSome(HttpServletRequest request , String name, Integer age){
        System.out.println("doSome name="+name+"   age="+age);
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname",name);
        mv.addObject("myage",age);
        mv.setViewName("show");

       // RequestDispatcher rd =request.getRequestDispatcher("/show.jsp");
       // rd.forward(request,null);

       // request.getRequestDispatcher("/show.jsp").forward(request,null);


        return mv;
    }



}
