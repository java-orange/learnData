ch08-forard-redirect:转发和重定向

forward：表示转发
redirect：表示重定向

forward和redirect都是关键字， 有一个共同的特点不和视图解析器一同工作



扩展：

forward和redirect他们都可以访问 视图文件，比如某个jsp ，html
 forward:/hello.jsp  forward:/main.html

forward和redirect他们都可以访问其它的controller
 forward:/some.do , redirect:/other.do

处理器方法可以返回ModelAndView, String , void 都可以使用forward，redirect