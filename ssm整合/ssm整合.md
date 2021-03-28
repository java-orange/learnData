# SSM整合



## 配置思路: 

> 1. 项目为war包
> 2. war包即为标准的web引用, 要拥有健全的web工程目录, resources/webapp/WEB-INF/
> 3. 访问首先通过项目启动tomcat, 访问webapp下的index.jsp
> 4. 若要访问
>    1. **动态资源**,
>    2. 则发送链接至tomcat,tomcat解析web.xml文件,根据<servlet-mapping>中的<url-pattern>匹配路径
>    3. 再根据<servlet>标签中的DispatcherServlet 进行转发, 因此需要初始化springMVC容器,便有了<init-param>来初始化
>    4. 再根据springMVC.xml中配置的controller扫描路径, 找寻对应的添加@controller注解的类,找到对应的@requestMapping()匹配路径
>    5. 再方法中完成数据的modelAndView写入
>    6. 此时,若在springMVC.xml中配置了InternalResourceViewResolver视图解析器,则根据其前后缀拼出完整文件路径,找寻资源,使用jstl语法,jsp语法写入后,再转交给DispatcherServlet, 再提交给Tomcat, 然后向用户展示
> 5. 若要访问
>    1. **静态资源**
>    2. 则根据<url-pattern>的匹配路径,若为 / 全体匹配, 则此时使得静态资源处理器失效
>    3. 需在springmvc.xml中配置静态资源处理器<mvc: resources >, 才能实现转发
>    4. 并且需要添加注解驱动, <mvc:annotation-driven />
> 6. 因组合mybatis, 则相关的数据aop,ioc等一律转交给spring容器
> 7. 在springmvc容器初始化时,配置 ContextLoaderListener 使用<context-param> 进行配置, 使得同时初始化spring容器
> 8. 在applicationContext.xml中, 获取响应的数据库信息,使用<context:property-placeholder > 进行数据库db.propertice的引用
> 9. 进行配置数据库 <bean dataSource>
> 10. 因mybatis使用sqlSessionFactory进行创建sqlSession会话, 所以配置<sqlSessionFactoryBean> 注入sqlSessionFactory
> 11. 此<sqlSessionFactory >中 , 可配置原先在mybatis中配置的相关数据
>     1. datasource的指定
>     2. configLocation  Mybatis全局配置文件的位置
>     3. mapperLocations   对应的mapper文件位置
>     4. typeAliasesPackage  相应别名包的配置
> 12. 在配置mybatis扫描的dao包 < bean class="mapperScannerConfiguer" >
> 13. 配置@service 注解所在的包 <context: component-scan base-package> 此时,定要除去@controller包, 不然会父子容器冲突
> 14. 因mybatis相关注解, 可配置注解 <bean DataSourceTransactionManager >
> 15. 同时开启 <tx: annotation-driven> 注解事务驱动
> 16. 关于mybatis.xml 文件, 可放置一些公共的配置在其中, <settings > 等等
> 17. 最后在web.xml中配置字符过滤器
>
> 



### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.bjpowernode</groupId>
    <artifactId>ch01-ssm</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

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
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.18</version>
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


</project>
```









### web.xml

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


<!--    注册spring监听器, 在启动springMVC时 启动spring容器 -->
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



### springmvc.xml

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

    <!--    添加注解驱动 因为resources 与 @requestMapping有冲突,-->
    <mvc:annotation-driven />

    <!--    静态资源解析器-->
    <mvc:resources mapping="/static/**" location="/static/" />

</beans>
```



### applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.alibaba.com/schema/stat"
       xsi:schemaLocation="http://www.springframework.org/schema/beans

       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.alibaba.com/schema/stat
       http://www.alibaba.com/schema/stat.xsd
">

<!--    扫描properties文件,连接数据源-->
    <context:property-placeholder location="classpath:conf/db.properties" />

<!--    声明数据源,连接数据库-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}" />
        <property name="url" value="${jdbc.url}" />
        <property name="username" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />
    </bean>

    <!--SqlSessionFactoryBean创建SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--    并配置相关属性 -->
        <property name="dataSource" ref="dataSource" />
        <property name="configLocation"  value="classpath:conf/mybatis.xml" />
        <property name="mapperLocations" value="classpath:com/bjpowernode/dao/*.xml"/>
        <property name="typeAliasesPackage" value="com.bjpowernode.pojo" />
    </bean>

    <!-- 使用mybatis 扫描dao包 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.bjpowernode.dao" />
    </bean>

    <!--声明service的注解@Service 所在的包名位置-->
    <context:component-scan base-package="com.bjpowernode" >
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--事务配置：注解的配置， aspectj的配置-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

<!--    开启事务注解驱动  -->
    <tx:annotation-driven />

</beans>
```



### mybatis.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

<!--    settings：控制mybatis全局行为-->
    <settings>
<!--         设置mybatis输出日志-->
         <setting name="logImpl" value="STDOUT_LOGGING"/>
     </settings>

</configuration>
```



### db.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/bjsxt
jdbc.username=root
jdbc.password=root
```





### 此外还有拦截器, AOP切面, 异常处理器

​	

```
   aop: spring管理
		不修改的情况下,实现增强功能,代码扩展
		记录日志, 
		事务管理的原理也是aop

​	拦截器: 拦截页面,故springmvc容器中
		* preHandle 预处理方法, 在controller之前执行, **门神**
		* postHandle 后处理方法, 在controller返回之后拦截,用于信息二次处理,加密等
		* afterCompletion 最后执行的方法, 视图处理完成后进行,一般用于释放资源等

​	异常处理器: 对于页面的加载, 故在springmvc容器中
		发现异常:
			* 记录日志,异常错误内容,发生时间,发生的具体方法
			* 通知相关管理人员,发送消息
			* 给用户友好提示

```



#### 	AOP: 切面编程

```xml
1. 在spring容器中进行扫描该切面所在的包

  <!--声明service的注解@Service 所在的包名位置-->
    <context:component-scan base-package="com.bjpowernode" >
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

这个配置足够

2. 配置切面代理对象
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>


3. 在aop所在的类上增加@component, @aspect 两个注解

4. 进行切入点抽取
//相同切入点抽取
@Pointcut(value = "execution(* com.atguigu.spring5.aopanno.User.add(..))")
public void pointdemo() {}

5. 对不同的方法进行不同的注解, 注明通知位置
//@Before注解表示作为前置通知   

*  before(前置通知),
*  afterReturning(后置通知),
*  after(最终通知),
*  afterThrowing(异常通知),
*  around(环绕通知) -> 参数ProceedingJoinPoing


@Before(value = "pointdemo()")
public void before() {
    System.out.println("before.........");
}
```





#### 	拦截器配置

1. 定制类, 并实现HandlerInterceptor 接口

2. 重写对应的方法

* preHandle 预处理方法, 在controller之前执行, **门神**
* postHandle 后处理方法, 在controller返回之后拦截,用于信息二次处理,加密等
* afterCompletion 最后执行的方法, 视图处理完成后进行,一般用于释放资源等

3. 在**springmvc.xml**中声明拦截器

   ```xml
   <mvc:interceptors>
   	<mvc:interceptor>
   		<mvc:mapping path="" />	拦截的地址
           <bean class="" />		拦截器对象
   	</mvc:interceptor>
   </mvc:interceptors>
   ```

4. **在前置拦截器,门神,预处理方法中, 若返回false, 想要给出提示信息,可以使用重定向**

   ```java
   // 使用重定向即可
   request.getRequestDispatcher("/tips.jsp").forward(request,response); 
   return false;
   ```

5. 多个拦截器时, 根据springmvc中注册的顺序决定执行的顺序,内部是ArrayList控制



#### 	异常处理 

1. 在**springMVC.xml**文件中配置需要扫描到该类的包
2. 定义类, 并在上面增加@controllerAdvice 注解
3. 对于不同的异常, 可在对应方法上面使用@ExceptionHandler 进行注解配置需要捕获的异常类
4. 对于其他异常,则只需注明@ExceptionHandler即可