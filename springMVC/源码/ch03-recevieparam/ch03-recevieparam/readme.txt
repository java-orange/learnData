ch03-recevieparam : 接收请求的参数
接收请求参数，使用的处理器方法的形参
1）HttpServletRequest
2)HttpServletResponse
3)HttpSession
4)用户提交的数据

接收用户提交的参数：
1.逐个接收
2.对象接收


注意：
 在提交请求参数时，get请求方式中文没有乱码。
 使用post方式提交请求，中文有乱码，需要使用过滤器处理乱码的问题。


过滤器可以自定义，也可使用框架中提供的过滤器 CharacterEncodingFilter



实现步骤：
1. 新建web maven工程
2. 加入依赖
   spring-webmvc依赖，间接把spring的依赖都加入到项目
   jsp，servlet依赖

3.重点： 在web.xml中注册springmvc框架的核心对象DispatcherServlet
   1)DispatcherServlet叫做中央调度器， 是一个servlet， 它的父类是继承HttpServlet
   2)DispatcherServlet页叫做前端控制器（front controller）
   3)DispatcherServlet负责接收用户提交的请求， 调用其它的控制器对象，
                      并把请求的处理结果显示给用户

4.创建一个发起请求的页面 index.jsp

5.创建控制器(处理器)类
  1）在类的上面加入@Controller注解，创建对象，并放入到springmvc容器中
  2）在类中的方法上面加入@RequestMapping注解。

6.创建一个作为结果的jsp，显示请求的处理结果。

7.创建springmvc的配置文件（spring的配置文件一样）
  1）声明组件扫描器， 指定@Contorller注解所在的包名
  2）声明视图解析器。帮助处理视图的。



