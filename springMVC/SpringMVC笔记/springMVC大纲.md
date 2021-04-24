# SpringMVC大纲指南

###### 	*本笔记配合pdf文件+csdn中springmvc收藏笔记使用*

## 1. SpringMVC的hello world

1. pom文件, 引入servlet-api  spring-webmvc 并指定为**war**包

2. web.xml文件, 初始化springmvc容器中央调度器, 并配置路径匹配

3. springmvc.xml 开启注解组件扫描

   ```xml
   <context:component-scan base-package="com.bjpowernode.controller"/>
   ```

4. 书写controller层, 使用modelandview写出数据,并指向页面

5. jsp页面中处理数据,

6. 配置tomcat,启动,访问完成

## 2.SpringMVC的认识

#### 	2.1 SpringMVC的执行流程

1. helloworld程序的执行流程: 网页请求发送至tomcat, tomcat寻找web.xml配置文件中的url-pattern

   ```xml
    <servlet-mapping>
           <servlet-name>springmvc</servlet-name>
           <url-pattern>*.do</url-pattern>
       </servlet-mapping>
   ```

2. 根据servlet-name找寻servlet的class,找到dispatchServlet

   ```xml
    <servlet>
           <servlet-name>springmvc</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
           <init-param>
               <param-name>contextConfigLocation</param-name>
               <param-value>classpath:springmvc.xml</param-value>
           </init-param>
           <load-on-startup>1</load-on-startup>
       </servlet>
   
   ```

3. 再根据springmvc.xml查找对应的@controller注解的类与@requestMapper("/some.do") 的方法, 
4. 此方法内部决定返回信息,并写入jsp文件中
5. 再将此jsp文件返回至tomcat并显示出来

* **简单来说: url -> DispatchServlet -> @controller -> @requestMapping -> jsp页面返回**

  图 - > springmvc.png

* 视图解析器配置相关的前置,后缀 在springmvc.xml中设置

  ```xml
  <!--    视图解析器 -->
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
  <!--        前缀,后缀-->
          <property name="prefix" value="/WEB-INF/view/"></property>
          <property name="suffix" value=".jsp"/>
      </bean>
  ```

#### 2.2 @requestMapping 注解的使用

* 注解在类上, 用以配置本类前置路径

* @requestMapping 指定请求方式方法,

* @requestMapping 处理请求参数 (**古老,落伍**)

  ```java
  @Controller
  public class MyController {
      @RequestMapping(value = "/some.do")
      public ModelAndView doSome(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session){
          ModelAndView mv = new ModelAndView();
          mv.addObject("msg","欢迎使用springmvc做web开发" + request.getParameter("name"));
          mv.addObject("fun","执行的是doSome方法");
          mv.setViewName("show");
          return mv;
      }
  ```

* #### ***接收参数:*** 

  * **逐个参数接收**

    * 将提交的表单name值与接收参数的形参名相同则可匹配, 与位置无关

    * ```html
      姓名：<input type="text" name="name"><br/>
      年龄：<input type="text" name="age"><br/>
      ```

      ```java
      public ModelAndView doSome(Integer age, String name){
              System.out.println("dosome方法的，name="+name+", age="+age);
      ```

    * ***400 错误, 是客户端提交数据参数过程中发生错误***

    * ***404 错误, 代表资源找不到***

    * *调用方法时，按名称对应，把接收的参数赋值给形参 doSome(strName,Integer.valueOf(strAge))      框架会提供类型转换的功能，把String转换为 int ，long ，float ，double等*

    * 因此, 若传入Null值会发生类型转换错误

    * 所以**在指定参数时, 使用包装类型** Integer类型, 可以防止传入空值时报错400, 但传入非法字符还是会400

    * 在提交请求参数时,get请求方式没有乱码,post方式提交会有乱码

  * **使用过滤器处理乱码问题**

    * 在web.xml文件中进行配置过滤器

      ```xml
      <!--    配置字符编码过滤器-->
          <filter>
              <filter-name>characterEncodingFilter</filter-name>
              <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
              <!--指定编码方式-->
              <init-param>
                  <param-name>encoding</param-name>
                  <param-value>utf-8</param-value>
              </init-param>
              <!--强制请求编码转换-->
              <init-param>
                  <param-name>forceRequestEncoding</param-name>
                  <param-value>true</param-value>
              </init-param>
              <!--强制响应编码转换-->
              <init-param>
                  <param-name>forceResponseEncoding</param-name>
                  <param-value>true</param-value>
              </init-param>
          </filter>
      <!--    配置过滤器的匹配-->
          <filter-mapping>
              <filter-name>characterEncodingFilter</filter-name>
      <!--        匹配所有-->
              <url-pattern>/*</url-pattern>
          </filter-mapping>
      
      ```

    * 若表单提交的name与方法处理的**形参**名称不同,则是用***@RequestParam***注解,

      * ```html
        <p>请求参数名和处理器方法的形参名不一样</p>
        <form action="other.do" method="post">
            姓名：<input type="text" name="iname"><br/>
            年龄：<input type="text" name="iage"><br/>
            <input type="submit" value="提交参数">
        </form>
        ```

        ```java
        public ModelAndView doOther(@RequestParam(value = "iname",required=false) String name,
                                    @RequestParam(value = "iage", required=false) Integer age){
        ```

        value -> 用于指定提交的name值

        required -> 用于指定是否必须

  * **对象参数接收参数值**

    * *使用对象接收参数,对象的类中的属性值名称需与提交的name值相同, 则SpringMVC自动调用该类的无参构造方法,根据传递的参数调用对应的set方法用以赋值,完成对象传参.*

  * **resful 风格传参**

    * 使用@PathVariable 注解用来接收

      ```java
    @GetMapping("/{id}/{name}")
      public void getPage(@PathVariable Integer id, @PathVariable String name){
          
      }
      ```
    ```
    
    ```
  
  ---
  
  ---
  
  
  
* #### ***处理器的返回值***
  
    * **ModelAndView** 		-> 				数据model 视图view
    
    * **String**                         ->                  只跳转页面,不传递数据 

      * 若配置了视图解析器, 则直接返回的 "show" 代表的是 该视图,
    * 若想要在String中也返回数据, 则可以通过httpServletRequest 作为参数,用来传递参数
        
* request.setAttribute("myName", name) 
        
  * **void**                            ->                 没用, 既不处理数据, 也不跳转页面, 可以处理ajax请求
      
* **Object**                       ->                  只处理数据, 精准处理ajax请求
    
  * 1. **增加json的工具库依赖, 默认使用jackson**
    
    2. **在springmvc.xml 中增加注解驱动** 
    
           ```xml
         <mvc:annotation-driven/>
           需要加的是: http://www.springframework.org/schema/mvc 对应的
         ```
         ```
      
         ```
    
      3. **在处理方法上增加@ResponseBody注解**
      
    * springMVC处理器返回object, 可以转为json输出至浏览器,响应ajax的内部原理
    
  * 添加依赖, json工具包
    
    * 注解驱动 	完成java对象转json格式
  * @responseBody  完成数据的写入response中
  
* **处理器返回List集合**
  
  * 也相当于object类型, 将List格式转为Json数组进行返回
  
  * **对于返回String类型数据** 若含有@requestBody注解, 则返回为数据, 若不含, 则是视图
  
    * 直接返回String,会存在中文乱码, 因为浏览器传递默认使用编码ISO-8859-1格式,
  
  * 需要在@requestMapping中添加属性,produces
  
  * ```java
      @RequestMapping(value = "/returnString.do", produces="text/plain;charset=utf-8")
    ```
  
  * **对于url-pattern中, 若配置为"/" 则静态资源不能访问**
  
    * 解决方式1: 添加默认处理器, **缺陷**: 依赖tomcat的默认处理器
  
      * 在Springmvc.xml 中增加
  
        ```xml
      <mvc:default-servlet-handler />
        // 也需添加注解驱动
      <mvc:annotation-driven />
        ```
  
    * 解决方式2: 添加i资源处理器(**重点**)

      * 在springmvc.xml中添加

        ```xml
        <mvc:resources mapping="/img/**" location="/img/" />
        <mvc:resources mapping="/css/**" location="/css/" />
      <mvc:resources mapping="/js/**" location="/js/" />
        
        //也需添加注解驱动
        <mvc:annotation-driven />
        ```
  
        * 因为若静态资源过多, 配置过多, **将所有静态资源放入 static 的文件夹内,static 位于webapp下, **指定一个静态资源处理器即可
  
        ```xml
        <mvc:resources mapping="/static/**" location="/static/" />
        ```
  
    **在html中, 跳转链接  地址中加不加 "/"**
  
    * 若不加"/" 则根据当前页的地址,后面直接拼接该路径
    
    * 若加"/" 则根据当前服务器的ip+端口直接拼接路径
  
    * **故在jsp页面中不用加"/"**  **controller中一定要+**
      
      * 需要在之前再增加${pageContext.request.contextPath}才能访问
      
    * ```
      3.参考地址
          1） 在你的页面中的，访问地址不加 "/"
      
      	 访问的是： http://localhost:8080/ch06_path/index.jsp
            路径： http://localhost:8080/ch06_path/
      		资源： index.jsp
      
          在index.jsp发起 user/some.do请求，访问地址变为 http://localhost:8080/ch06_path/user/some.do
      	   当你的地址 没有斜杠开头,例如 user/some.do , 当你点击链接时， 访问地址是当前页面的地址
      		加上链接的地址。
            http://localhost:8080/ch06_path/ + user/some.do
            
           -------------------------------------------------------------
           
      	  index.jsp  访问 user/some.do  ， 返回后现在的地址： http://localhost:8080/ch06_path/user/some.do
      
      	  http://localhost:8080/ch06_path/user/some.do
      	  路径：	  http://localhost:8080/ch06_path/user/
      	  资源：   some.do
      
      	  在index.jsp在 user/some.do ，就变为 http://localhost:8080/ch06_path/user/user/some.do
      
      	  解决方案：
      	   1.加入${pageContext.request.contextPath}
      		2.加入一个base标签， 是html语言中的标签。 表示当前页面中访问地址的基地址。
      		  你的页面中所有 没有“/”开头的地址，都是以base标签中的地址为参考地址
              使用base中的地址 + user/some.do 组成访问地址
      
      
      
      
         2）在你的页面中的，访问地址加 "/"
            访问的是： http://localhost:8080/ch06_path/index.jsp
            路径： http://localhost:8080/ch06_path/
      		资源： index.jsp
      
      		点击 /user/some.do, 访问地址变为 http://localhost:8080/user/some.do
      		参考地址是 你的服务器地址， 也就是 http://localhost:8080
      
      
      		如果你的资源不能访问： 加入${pageContext.request.contextPath}
      		<a href="${pageContext.request.contextPath}/user/some.do">发起user/some.do的get请求</a>
      
      
      	
      
      index.jsp--addStudent.jsp---student/addStudent.do( service的方法，调用dao的方法)--result.jsp
      ```
    
      
    
    在博客中 "地址分类" 中写的很清楚,
    
    **在jsp页面中, 为了防止意外情况导致资源路径不正确, 使用完全策略, <base/>标签**
    
    ```
    加入一个base标签， 是html语言中的标签。 表示当前页面中访问地址的基地址。
    		  你的页面中所有 没有“/”开头的地址，都是以base标签中的地址为参考地址
            使用base中的地址 + user/some.do 组成访问地址     
    ```
    
    ```html
    <% 
    	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" ;
    %>
        上面的定义位于<!DOCTYPE html> 之上
        <base href="<%=basePath%>" />    base 位于head里面
    ```
    
    

## 3.SSM整合开发

* ### pom文件

  ```xml
  
       指定为war包
  	指定为war包
      <!--指定编译版本,编译字符-->
      <properties>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          <maven.compiler.source>1.8</maven.compiler.source>
          <maven.compiler.target>1.8</maven.compiler.target>
      </properties>
  
      <dependencies>
          <!--junit 测试包-->
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.11</version>
              <scope>test</scope>
          </dependency>
          <!--servlet依赖-->
          <dependency>
              <groupId>javax.servlet</groupId>
              <artifactId>javax.servlet-api</artifactId>
              <version>3.1.0</version>
              <scope>provided</scope>
          </dependency>
          <!-- jsp依赖 -->
          <dependency>
              <groupId>javax.servlet.jsp</groupId>
              <artifactId>jsp-api</artifactId>
              <version>2.2.1-b03</version>
              <scope>provided</scope>
          </dependency>
          <!--springmvc依赖-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-webmvc</artifactId>
              <version>5.2.5.RELEASE</version>
          </dependency>
          <!--spring 事务依赖-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-tx</artifactId>
              <version>5.2.5.RELEASE</version>
          </dependency>
          <!--spring-jdbc依赖-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-jdbc</artifactId>
              <version>5.2.5.RELEASE</version>
          </dependency>
          <!--jackson 依赖-->
          <dependency>
              <groupId>com.fasterxml.jackson.core</groupId>
              <artifactId>jackson-core</artifactId>
              <version>2.9.0</version>
          </dependency>
          <dependency>
              <groupId>com.fasterxml.jackson.core</groupId>
              <artifactId>jackson-databind</artifactId>
              <version>2.9.0</version>
          </dependency>
          <!--mybatis-spring 依赖-->
          <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis-spring</artifactId>
              <version>1.3.1</version>
          </dependency>
          <!--mybatis依赖 -->
          <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis</artifactId>
              <version>3.5.1</version>
          </dependency>
          <!--mysql依赖 -->
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>5.1.9</version>
          </dependency>
          <!--druid连接池依赖 -->
          <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>druid</artifactId>
              <version>1.1.12</version>
          </dependency>
      </dependencies>
  
      <build>
  <!--        用于编译时将文件带上-->
          <resources>
              <resource>
                  <directory>src/main/java</directory><!--所在的目录-->
                  <includes><!--包括目录下的.properties,.xml 文件都会扫描到-->
                      <include>**/*.properties</include>
                      <include>**/*.xml</include>
                  </includes>
                  <filtering>false</filtering>
              </resource>
          </resources>
          <plugins>
              <plugin>
                  <artifactId>maven-compiler-plugin</artifactId>
                  <version>3.1</version>
                  <configuration>
                      <source>1.8</source>
                      <target>1.8</target>
                  </configuration>
              </plugin>
          </plugins>
      </build>
  
  ```

* ### web.xml文件

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
  
  <!--    注册中央调度器-->
      <servlet>
          <servlet-name>springmvc</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:conf/springmvc.xml</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
  
      <servlet-mapping>
          <servlet-name>springmvc</servlet-name>
          <url-pattern>/</url-pattern>			
      </servlet-mapping>
  
  
  <!--    注册spring监听器-->
      <context-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:conf/applicationContext.xml</param-value>
      </context-param>
      <listener>
          <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
      </listener>
  
  <!--    设置字符编码-->
      <filter>
          <filter-name>characterEncodingFilter</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <init-param>
              <param-name>encoding</param-name>
              <param-value>utf-8</param-value>
          </init-param>
          <init-param>
              <param-name>forceRequestEncoding</param-name>
              <param-value>true</param-value>
          </init-param>
          <init-param>
              <param-name>forceResponseEncoding</param-name>
              <param-value>true</param-value>
          </init-param>
      </filter>
  
      <filter-mapping>
          <filter-name>characterEncodingFilter</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  </web-app>
  ```

* ### springmvc.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:mvc="http://www.springframework.org/schema/mvc"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         https://www.springframework.org/schema/context/spring-context.xsd
         http://www.springframework.org/schema/mvc
         https://www.springframework.org/schema/mvc/spring-mvc.xsd">
  
  <!--    组件扫描,将controller组件注册至springmvc容器中-->
      <context:component-scan base-package="com.bjpowernode.controller" />
  
  <!--    视图解析器-->
      <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
          <property name="prefix" value="/WEB-INF/jsp/" />
          <property name="suffix" value=".jsp" />
      </bean>
  
      <!--    添加注解驱动-->
      <mvc:annotation-driven />
  
      <!--    静态资源解析器-->
      <mvc:resources mapping="/static/**" location="/static/" />    <!-- static文件夹放在webapp下面 -->
  
  </beans>
  ```

* ### applicationContext.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         https://www.springframework.org/schema/context/spring-context.xsd">
  
  <!--    扫描properties文件,连接数据源-->
      <context:property-placeholder location="classpath:conf/db.properties" />
  
  <!--    声明数据源,连接数据库-->
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
          <property name="url" value="${jdbc.url}" />
          <property name="username" value="${jdbc.username}" />
          <property name="password" value="${jdbc.password}" />
      </bean>
  
      <!--SqlSessionFactoryBean创建SqlSessionFactory-->
      <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
          <property name="dataSource" ref="dataSource" />
          <property name="configLocation"  value="classpath:conf/mybatis.xml" />
      </bean>
  
      <!--声明mybatis的扫描器，创建dao对象-->
      <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
          <property name="basePackage" value="com.bjpowernode.dao" />
      </bean>
  
  
      <!--声明service的注解@Service所在的包名位置-->
      <context:component-scan base-package="com.bjpowernode.service" />
  
      <!--事务配置：注解的配置， aspectj的配置-->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
  
  <!--    开启注解驱动事务 -->
      <tx:annotation-driven />
  
  </beans>
  ```

* ### db.properties

  ```properties
  jdbc.url=jdbc:mysql://localhost:3306/bjsxt
  jdbc.username=root
  jdbc.password=root
  ```

* ### mybatis.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <!--settings：控制mybatis全局行为-->
      <!-- <settings>
           &lt;!&ndash;设置mybatis输出日志&ndash;&gt;
           <setting name="logImpl" value="STDOUT_LOGGING"/>
       </settings>-->
  
      <!--设置别名-->
      <typeAliases>
          <!--name:实体类所在的包名(不是实体类的包名也可以)-->
          <package name="com.bjpowernode.pojo"/>
      </typeAliases>
  
      <!-- sql mapper(sql映射文件)的位置-->
      <mappers>
          <!--
            name：是包名， 这个包中的所有mapper.xml一次都能加载
            使用package的要求：
             1. mapper文件名称和dao接口名必须完全一样，包括大小写
             2. mapper文件和dao接口必须在同一目录
          -->
          <package name="com.bjpowernode.dao"/>
      </mappers>
  
  </configuration>
  ```

* 对于ajax请求

  ```html
  <%@ page contentType="text/html; charset=UTF-8" language="java" %>
  <%
      String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" ;
  %>
  <!DOCTYPE html>
  <html lang="zh-CN">
  <head>
      <title>信息提示</title>
      <base href="<%=basePath%>">
      <script type="text/javascript" src="static/js/jquery-3.4.1.js"></script>
      <script type="text/javascript" >
          $(function () {
              loadData();			// 使得刚进入页面就进行请求数据
              $("#btn").click(function () {
                  loadData()		// 点击请求数据
              })
          })
          function loadData() {
              $.ajax({
                  url: "student/findAllStudent",
                  type: "get",
                  dataType: "json",
                  success: function (data) {
                      $("#context").html("");         //每次请求先清空之前的数据
                      $.each(data, function (i, n) {
                          $("#context").append(n.id + "     " + n.name  + "     "+ n.age).append("<br/>")
                      })
                  }
              })
          }
      </script>
  </head>
  <body>
      <h2>${tips}</h2>
  
      <p id="context">
  
      </p>
      <button id="btn">查看人名单</button>
  
  </body>
  </html>
  
  ```

## 4.SpringMVC核心技术

* forward: 转发, **内部跳转并返回**
  * mv.setViewName("forward:视图文件完整路径");
  * 特性: 可以跨过视图解析器,就当此请求不经过视图解析器
* redirect: 重定向, **返回给客户新的连接,让客户重新请求**, **不能访问WEB-INF**
  * mv.setViewName("forward:视图文件完整路径");
  * 特性: 可以跨过视图解析器,就当此请求不经过视图解析器

## 5.异常处理

​	**@controllerAdvice**, 此类需在springmvc.xml中配置扫描

​	**@ExceptionHandler**	处理的异常方法

​	**在springmvc中扫描handler包,** 

​	全局异常处理器

* 发现异常:
  * 记录日志,异常错误内容,发生时间,发生的具体方法
  * 通知相关管理人员,发送消息
  * 给用户友好提示
  
*  

  

*  ```java
  package com.bjpowernode.handler;
  
  import com.bjpowernode.exception.UserException;
  import org.springframework.web.bind.annotation.ControllerAdvice;
  import org.springframework.web.bind.annotation.ExceptionHandler;
  import org.springframework.web.servlet.ModelAndView;
  
  @ControllerAdvice
  public class GlobalExceptrionHandler {
  
      // 指定userException 的异常处理器
      @ExceptionHandler(UserException.class)
      public ModelAndView handleUserName(Exception e) {
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.addObject("msg", e.getMessage());
          modelAndView.setViewName("error");
          return modelAndView;
      }
      
      // 其余所有错误的异常处理器
      @ExceptionHandler()
      public ModelAndView all(Exception e) {
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.addObject("msg", e.getMessage());
          modelAndView.setViewName("error");
          return modelAndView;
      }
  
  }
  
  ```

  

## 6.拦截器

SpringMVC中的Interceptor拦截器是非常重要和相当有用的，它的主要作用是**拦截指定的用户请求**，并进行相应的预处理与后处理。其拦截的**时间点在“处理器映射器根据用户提交的请求映射出了所要执行的处理器类，并且也找到了要执行该处理器类的处理器适配器，在处理器适配器执行处理器之前”**。当然，在处理器映射器映射出所要执行的处理器类时，已经将拦截器与处理器组合为了一个处理器执行链，并返回给了中央调度器。



* 拦截器和springmvc中一种,需要**实现HandlerInterceptor接口**

  * preHandle 预处理方法, 在controller之前执行, **门神**
  * postHandle 后处理方法, 在controller返回之后拦截,用于信息二次处理,加密等
  * afterCompletion 最后执行的方法, 视图处理完成后进行,一般用于释放资源等

* 在springmvc.xml中声明拦截器

  ```xml
  <mvc:interceptors>
  	<mvc:interceptor>
  		<mvc:mapping path="" />	拦截的地址
          <bean class="" />		拦截器对象
  	</mvc:interceptor>
  </mvc:interceptors>
  ```

* **在前置拦截器,门神,预处理方法中, 若返回false, 想要给出提示信息,可以使用重定向**

  ```java
  // 使用重定向即可
  request.getRequestDispatcher("/tips.jsp").forward(request,response); 
  return false;
  ```

* 多个拦截器时, 根据springmvc中注册的顺序决定执行的顺序,内部是ArrayList控制

  ```
  多个拦截器：
  第一个拦截器preHandle=true , 第二个拦截器preHandle=true 
  
  111111-拦截器的MyInterceptor的preHandle()
  22222-拦截器的MyInterceptor的preHandle()
  =====执行MyController中的doSome方法=====
  22222-拦截器的MyInterceptor的postHandle()
  111111-拦截器的MyInterceptor的postHandle()
  22222-拦截器的MyInterceptor的afterCompletion()
  111111-拦截器的MyInterceptor的afterCompletion()
  
  ---------------------------------------------------
  第一个拦截器preHandle=true , 第二个拦截器preHandle=false
  
  111111-拦截器的MyInterceptor的preHandle()
  22222-拦截器的MyInterceptor的preHandle()
  111111-拦截器的MyInterceptor的afterCompletion()
  
  ----------------------------------------------------------
  第一个拦截器preHandle=false , 第二个拦截器preHandle=true|false
  
  111111-拦截器的MyInterceptor的preHandle()
  
  
  ====================================================================
  拦截器和过滤器的区别
  
  1.过滤器是servlet中的对象，  拦截器是框架中的对象
  2.过滤器实现Filter接口的对象， 拦截器是实现HandlerInterceptor
  3.过滤器是用来设置request，response的参数，属性的，侧重对数据过滤的。
    拦截器是用来验证请求的，能截断请求。
  4.过滤器是在拦截器之前先执行的。
  5.过滤器是tomcat服务器创建的对象
    拦截器是springmvc容器中创建的对象
  6.过滤器是一个执行时间点。
    拦截器有三个执行时间点
  7.过滤器可以处理jsp，js，html等等
    拦截器是侧重拦截对Controller的对象。 如果你的请求不能被DispatcherServlet接收， 这个请求不会执行拦截器内容
  8.拦截器拦截普通类方法执行，过滤器过滤servlet请求响应
  ```

  ```java
  package com.bjpowernode.interceptor;
  
  
  import lombok.extern.java.Log;
  import lombok.extern.log4j.Log4j;
  import org.springframework.web.servlet.HandlerInterceptor;
  import org.springframework.web.servlet.ModelAndView;
  
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  
  /**
   * 拦截器
   */
  @Log
  public class MyInterceptor implements HandlerInterceptor {
  
      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          log.info("进入前置处理器");
          // 使用重定向即可, 若使用false 拦截, 使用重定向设置位置
  //        request.getRequestDispatcher("register").forward(request,response);
          return true;
      }
  
      @Override
      public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
          log.info("进入后置处理器");
  
      }
  
      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
          log.info("进入final处理器");
  
      }
  }
  
  ```



springmvc.xml

```xml

    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.bjpowernode.interceptor.MyInterceptor" />
        </mvc:interceptor>
    </mvc:interceptors>


```



​	

## 7.关于拦截器与过滤器

**1. 过滤器（Filter）**

首先说一下Filter的使用地方，我们在配置web.xml时，总会配置下面一段设置字符编码，不然会导致乱码问题：

```xml
<filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>

<filter-mapping>
    <filter-name>encoding</filter-name>
    <servlet-name>/*</servlet-name>
</filter-mapping>

```

配置这个地方的目的，是让所有的请求都需要进行字符编码的设置，下面来介绍一下Filter。

（1）**过滤器(Filter)**：它依赖于servlet容器。在实现上，基于函数回调，它可以对几乎所有请求进行过滤，但是缺点是一个过滤器实例只能在容器初始化时调用一次。使用过滤器的目的，是用来做一些过滤操作，获取我们想要获取的数据，比如：在Javaweb中，对传入的request、response提前过滤掉一些信息，或者提前设置一些参数，然后再传入servlet或者Controller进行业务逻辑操作。通常用的场景是：在过滤器中修改字符编码（CharacterEncodingFilter）、在过滤器中修改HttpServletRequest的一些参数（XSSFilter(自定义过滤器)），如：过滤低俗文字、危险字符等。

**2、拦截器（Interceptor）**
拦截器的配置一般在SpringMVC的配置文件中，使用Interceptors标签，具体配置如下：

```xml
<mvc:interceptors>
    <mvc:interceptor>
        <mvc:mapping path="/**" />
        <bean class="com.scorpios.atcrowdfunding.web.LoginInterceptor"></bean>
    </mvc:interceptor>
    <mvc:interceptor>
        <mvc:mapping path="/**" />
        <bean class="com.scorpios.atcrowdfunding.web.AuthInterceptor"></bean>
    </mvc:interceptor>
</mvc:interceptors>

```

（2）**拦截器（Interceptor）**：它依赖于web框架，在SpringMVC中就是依赖于SpringMVC框架。在实现上,基于Java的反射机制，属于面向切面编程（AOP）的一种运用，是在 getHandler 时生成的 HanlderExcuteChain ，该对象里包含了 interceptor 和 handerMethod ，而并非把 interceptor 解析成 advise ，然后为每个 controller 生成 AopProxy。就是在service或者一个方法前，调用一个方法，或者在方法后，调用一个方法，比如动态代理就是拦截器的简单实现，在调用方法前打印出字符串（或者做其它业务逻辑的操作），也可以在调用方法后打印出字符串，甚至在抛出异常的时候做业务逻辑的操作。由于拦截器是基于web框架的调用，因此可以使用Spring的依赖注入（DI）进行一些业务操作，同时一个拦截器实例在一个controller生命周期之内可以多次调用。

SpringMVC中的Interceptor拦截器是非常重要和相当有用的，它的主要作用是**拦截指定的用户请求**，并进行相应的预处理与后处理。其拦截的**时间点在“处理器映射器根据用户提交的请求映射出了所要执行的处理器类，并且也找到了要执行该处理器类的处理器适配器，在处理器适配器执行处理器之前”**。当然，在处理器映射器映射出所要执行的处理器类时，已经将拦截器与处理器组合为了一个处理器执行链，并返回给了中央调度器。







------

下面在一个项目中我们使用既有多个过滤器，又有多个拦截器，并观察它们的执行顺序：
**（1）第一个过滤器：**

```java
public class TestFilter1 implements Filter {  
  
		@Override
  	    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {  
        //在DispatcherServlet之前执行  
		System.out.println("############TestFilter1 doFilterInternal executed############");  
        filterChain.doFilter(request, response);  
        //在视图页面返回给客户端之前执行，但是执行顺序在Interceptor之后  
        System.out.println("############TestFilter1 doFilter after############");  
    }  
}  

```



**（2）第二个过滤器：**

```java

public class TestFilter2 implements Filter {  
 
	@Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {  
	    //在DispatcherServlet之前执行  
        System.out.println("############TestFilter2 doFilterInternal executed############");  
        filterChain.doFilter(request, response);  
        //在视图页面返回给客户端之前执行，但是执行顺序在Interceptor之后 
        System.out.println("############TestFilter2 doFilter after############");  
    }  
}  

```

**（3）在web.xml中注册这两个过滤器：**

```xml
	<!-- 自定义过滤器：testFilter1 -->   
	   <filter>  
	        <filter-name>testFilter1</filter-name>  
	        <filter-class>com.scorpios.filter.TestFilter1</filter-class>  
	    </filter>  
	    <filter-mapping>  
	        <filter-name>testFilter1</filter-name>  
	        <url-pattern>/*</url-pattern>  
	    </filter-mapping>  
	    <!-- 自定义过滤器：testFilter2 -->   
	   <filter>  
	        <filter-name>testFilter2</filter-name>  
	        <filter-class>com.scorpios.filter.TestFilter2</filter-class>  
	    </filter>  
	    <filter-mapping>  
	        <filter-name>testFilter2</filter-name>  
	        <url-pattern>/*</url-pattern>  
	    </filter-mapping>  

```

**再定义两个拦截器：**
**（4）第一个拦截器：**

```java
public class BaseInterceptor implements HandlerInterceptor{  
     
    /** 
     * 在DispatcherServlet之前执行 
     * */  
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {  
        System.out.println("************BaseInterceptor preHandle executed**********");  
        return true;  
    }  
 
    /** 
     * 在controller执行之后的DispatcherServlet之后执行 
     * */  
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {  
        System.out.println("************BaseInterceptor postHandle executed**********");  
    }  
     
    /** 
     * 在页面渲染完成返回给客户端之前执行 
     * */  
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)  
            throws Exception {  
        System.out.println("************BaseInterceptor afterCompletion executed**********");  
    }  
}  

```

**（5）第二个拦截器：**

```java
public class TestInterceptor implements HandlerInterceptor {  
 
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {  
        System.out.println("************TestInterceptor preHandle executed**********");  
        return true;  
    }  
 
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {  
        System.out.println("************TestInterceptor postHandle executed**********");  
    }  
 
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {  
        System.out.println("************TestInterceptor afterCompletion executed**********");  
    }  
}  

```

**（6）、在SpringMVC的配置文件中，加上拦截器的配置：**

```xml
	<!-- 拦截器 -->  
	<mvc:interceptors>  
	    <!-- 对所有请求都拦截，公共拦截器可以有多个 -->  
	    <bean name="baseInterceptor" class="com.scorpios.interceptor.BaseInterceptor" />  
		
		<mvc:interceptor> 
		    <!-- 对/test.html进行拦截 -->       
	        <mvc:mapping path="/test.html"/>  
	        <!-- 特定请求的拦截器只能有一个 -->  
	        <bean class="com.scorpios.interceptor.TestInterceptor" />  
	    </mvc:interceptor>  
	</mvc:interceptors>  

```

**（7）、定义一个Controller控制器：**

```java
package com.scorpios.controller;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.servlet.ModelAndView;  
  
@Controller  
public class TestController {  
    @RequestMapping("/test")  
    public ModelAndView handleRequest(){  
        System.out.println("---------TestController executed--------");  
        return new ModelAndView("test");  
    }  
}  

```

**（8）、测试结果：**
启动测试项目，地址如下：http://www.localhost:8080/demo，可以看到控制台中输出如下

![image-20210424185259521](.\img\test1.png)

这就说明了过滤器的运行是依赖于servlet容器，跟springmvc等框架并没有关系。并且，多个过滤器的执行顺序跟xml文件中定义的先后关系有关。

接着清空控制台，并访问：http://www.localhost:8080/demo/test，再次看控制台的输出：

![](.\img\test2.png)

从这个控制台打印输出，就可以很清晰地看到有多个拦截器和过滤器存在时的整个执行顺序了。当然，对于多个拦截器它们之间的执行顺序跟在SpringMVC的配置文件中定义的先后顺序有关。

**四、总结**

对于上述过滤器和拦截器的测试，可以得到如下结论：
（1）、Filter需要在web.xml中配置，依赖于Servlet；
（2）、Interceptor需要在SpringMVC中配置，依赖于框架；
（3）、Filter的执行顺序在Interceptor之前，

（4）、两者的本质区别：拦截器（Interceptor）是基于Java的反射机制，而过滤器（Filter）是基于函数回调。从灵活性上说拦截器功能更强大些，Filter能做的事情，都能做，而且可以在请求前，请求后执行，比较灵活。Filter主要是针对URL地址做一个编码的事情、过滤掉没用的参数、安全校验（比较泛的，比如登录不登录之类），太细的话，还是建议用interceptor。不过还是根据不同情况选择合适的。





## **整个url的执行流程**



<img src=".\img\容器.png" style="zoom:50%;" />



## 

请求先被**tomcat**捕获，

经过**filter**过滤器，

传递给**dispatcherServlet**，

**dispatcherServlet**根据**handlemapping**进行控制器映射，寻找对应的controller中的对应的方法，

再经过**handlerAdapter**进行参数与返回值的处理器映射，

再经过**handlerInterceptor** 拦截器进行拦截（**preHandle**方法）

执行切面aop

再经过**controller**层进行业务处理

然后再走**handlerInterceptor** 拦截器的后置拦截（**postHandle**方法）

再交予**dispatcherServlet**

再进行页面渲染**viewResolver**

后台自动走**handlerInterceptor** 拦截器的最终拦截（**afterCompletion**方法）  清理资源

再走**filter**进行过滤

最后返回给**tomcat**

响应页面

---




