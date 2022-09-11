package com.atguigu.controller;

import com.atguigu.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author maomao
 * @create 2022-07-26 22:35
 */
@Controller
//@RestController
public class TestAjaxController {

    @RequestMapping("/test/ajax")
    public void testAjax(Integer id, @RequestBody String requestBody, HttpServletResponse response) throws IOException {
        System.out.println("requestBody"+requestBody);
        System.out.println("id"+id);
        response.getWriter().write("hello,axios");
    }
    //将json格式的数据转换为实体类对象
    @RequestMapping("/test/RequestBody/json")
    public void testRequestBody(@RequestBody User user,
                                HttpServletResponse response) throws IOException {
        System.out.println(user);
        //User{id=null, username='admin', password='123456', age=23, gender='男'}
        response.getWriter().print("hello,RequestBody");
    }


//    //将json格式的数据转换为map集合
//    @RequestMapping("/test/RequestBody/json")
//    public void testRequestBody(@RequestBody Map<String, Object> map,
//                                HttpServletResponse response) throws IOException {
//        System.out.println(map);
//        //{username=admin, password=123456, age=23, gender=男}
//        response.getWriter().print("hello,RequestBody");
//    }

//    @RequestMapping("/test/ResponseBody")
//    public String testResponseBody(){
//        //此时会跳转到逻辑视图success所对应的页面
//        return "success";
//    }

//    @RequestMapping("/test/ResponseBody")
//    @ResponseBody
//    public String testResponseBody(){
//        //此时响应浏览器数据success
//        return "success";
//    }

//    //响应浏览器map集合
//    @RequestMapping("/test/ResponseBody/json")
//    @ResponseBody
//    public Map<String, Object> testResponseBody(){
//        User user1 = new User(1001,"admin1","123456",23,"男");
//        User user2 = new User(1002,"admin2","123456",23,"男");
//        User user3 = new User(1003,"admin3","123456",23,"男");
//        Map<String, Object> map = new HashMap<>();
//        map.put("1001", user1);
//        map.put("1002", user2);
//        map.put("1003", user3);
//        return map;
////        Object
////        1001: {id: 1001, username: 'admin1', password: '123456', age: 23, gender: '男'}
////        1002: {id: 1002, username: 'admin2', password: '123456', age: 23, gender: '男'}
////        1003: {id: 1003, username: 'admin3', password: '123456', age: 23, gender: '男'}
//    }

//    //响应浏览器实体类对象
//    @RequestMapping("/test/ResponseBody/json")
//    @ResponseBody
//    public User testResponseBody(){
//        User user = new User(1001,"admin1","123456",23,"男");
//        return user;
////        {id: 1001, username: 'admin1', password: '123456', age: 23, gender: '男'}
////        age: 23
////        gender: "男"
////        id: 1001
////        password: "123456"
////        username: "admin1"
//    }
    //响应浏览器list集合
    @RequestMapping("/test/ResponseBody/json")
    @ResponseBody
    public List<User> testResponseBody(){
        User user1 = new User(1001,"admin1","123456",23,"男");
        User user2 = new User(1002,"admin2","123456",23,"男");
        User user3 = new User(1003,"admin3","123456",23,"男");
        List<User> list = Arrays.asList(user1, user2, user3);
        return list;
//        (3) [{…}, {…}, {…}]
//        0: {id: 1001, username: 'admin1', password: '123456', age: 23, gender: '男'}
//        1: {id: 1002, username: 'admin2', password: '123456', age: 23, gender: '男'}
//        2: {id: 1003, username: 'admin3', password: '123456', age: 23, gender: '男'}
    }


}
