# Springboot2 

[讲义来源](https://www.yuque.com/atguigu/springboot)

[多看文档才是正道](spring.io)

## Springboot2入门

### 1.Maven 镜像设置， jdk设置

```xml
<mirrors>
      <mirror>
        <id>nexus-aliyun</id>
        <mirrorOf>central</mirrorOf>
        <name>Nexus aliyun</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public</url>
      </mirror>
  </mirrors>
 
  <profiles>
         <profile>
              <id>jdk-1.8</id>
              <activation>
                <activeByDefault>true</activeByDefault>
                <jdk>1.8</jdk>
              </activation>
              <properties>
                <maven.compiler.source>1.8</maven.compiler.source>
                <maven.compiler.target>1.8</maven.compiler.target>
                <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
              </properties>
         </profile>
  </profiles>
```



### 2.引入依赖

```xml
	<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
    </parent>

<!-- 
1、spring-boot-starter-* ： *就某种场景， springboot官方提供的启动器
2、只要引入starter，这个场景的所有常规需要的依赖我们都自动引入
3、SpringBoot所有支持的场景
https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter
4、见到的  *-spring-boot-starter： 第三方为我们提供的简化开发的场景启动器。
-->
    <dependencies>
        <!-- web开发所需依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

    </dependencies>

	<build>
		<plugins>
			<!-- spring的maven打包为可运行jar包的插件-->
        	<plugin>
                <groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>            	
            </plugin>
        </plugins>
	</build>

```

### 3.创建主程序

```java
/**
 * 主程序类
 * @SpringBootApplication：这是一个SpringBoot应用
   @SpringBootApplication 是一个复合注解，
 		底层是@SpringBootConfiguation
 			 @EnableAutoConfiguration
 			 @ComponentScan 
         三个注解的合成体
 */
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
```

### 4.直接编写业务

```java
// RestController 注解 -> 融合controller注解与responsebody注解
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String handle01(){
        return "Hello, Spring Boot 2!";
    }
}
```

### 5.编写配置

```xml
 <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

### 6.若要更换版本

```xml
<！-- 1、查看spring-boot-dependencies里面规定当前依赖的版本 用的 key。
2、在当前项目里面重写配置 -->
    <properties>
        <mysql.version>5.1.43</mysql.version>
    </properties>
```

### 7.若想要改变扫描路径

​	主程序所在包及其下面的所有子包里面的组件都会被默认扫描进来

​	无需以前的包扫描配置

```java
@SpringBootApplication(scanBasePackages = "com.atguigu")     // 扩大基础扫描包
```

### 8.容器功能-组件添加

​	使用@Configuration 注解， 声明此类为配置类即可。

```java
#############################Configuration使用示例######################################################
/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proxyBeanMethods：代理bean的方法
 		默认不配置属于单例模式
 *      Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】
 		更改为false，则为原型模式，使用时创建再加载。
 *      Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 *      组件依赖必须使用Full模式默认。其他默认是否Lite模式
 *
 *
 *
 */
//@Configuration(proxyBeanMethods = false) //告诉SpringBoot这是一个配置类 == 配置文件
@Configuration
public class MyConfig {

    /**
     * Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
     * @return
     */
    @Bean //给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public User user01(){
        User zhangsan = new User("zhangsan", 18);
        //user组件依赖了Pet组件
        zhangsan.setPet(tomcatPet());
        return zhangsan;
    }

    @Bean("tom")	// 定义bean的id
    public Pet tomcatPet(){
        return new Pet("tomcat");
    }
}


################################@Configuration测试代码如下########################################
@SpringBootApplication
@ComponentScan("com.atguigu.boot")
public class MainApplication {

    public static void main(String[] args) {
        //1、返回我们IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);

        //2、查看容器里面的组件, 查看所有组件的名称。
        String[] names = run.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        //3、从容器中获取组件

        Pet tom01 = run.getBean("tom", Pet.class);

        Pet tom02 = run.getBean("tom", Pet.class);

        System.out.println("组件："+(tom01 == tom02));

        //4、若不进行配置@Configuration(proxyBeanMethods = false)， 默认为true.
        //   则所有的bean对象均有Spring代理对象进行创建。所有对象均为单例模式。 
        //该配置类代理对象为：com.atguigu.boot.config.MyConfig$$EnhancerBySpringCGLIB$$51f1e1ca@1654a892
        MyConfig bean = run.getBean(MyConfig.class);
        System.out.println(bean);

        User user = bean.user01();
        User user1 = bean.user01();
        System.out.println(user == user1);


        User user01 = run.getBean("user01", User.class);
        Pet tom = run.getBean("tom", Pet.class);

        System.out.println("用户的宠物："+(user01.getPet() == tom));

        
        // 判断容器中是否含有某个组件
        boolean tom = run.containsBean("tom");
        System.out.println("容器中是否含有tom组件" + tom)；
        
        
    }
}
```

### 9.常用的组件扫描注解（所有注解解释）

- ###### @Bean（常规bean标签）

- ###### @Component（组件类）

- ###### @Controller（控制器）

- ###### @Service（业务）

- ###### @Repository（DAO层）

- ###### @ComponentScan（扫描包）

- ###### @Import（导入功能）

  ```java
   * 
       给容器中注册组件：
       1. 包扫描+组件标注注解（@Controller,@Service,@Repository,@Component）
       2. @Bean[导入第三方包中的组件]
       3、@Import({User.class, DBHelper.class})
   *      给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名
   */
  
  @Import({User.class, DBHelper.class})
  @Configuration(proxyBeanMethods = false) //告诉SpringBoot这是一个配置类 == 配置文件
  public class MyConfig {
  }
  ```

- ###### @Conditional（条件配置）

  - 条件装配：满足Conditional指定的条件，则进行组件注入

  - ![](\img\conditional.png)

  - ```java
    =====================测试条件装配==========================
    @Configuration(proxyBeanMethods = false) //告诉SpringBoot这是一个配置类 == 配置文件
    //@ConditionalOnBean(name = "tom")		// 若容器中存在tom组件，才生效。可专配再方法中，装配在类上，不满足条件，整个类不加载，所有方法不执行。
    @ConditionalOnMissingBean(name = "tom")	// 若容器中不存在tom组件，则加载
    public class MyConfig {
    
        @Bean //给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
        public User user01(){
            User zhangsan = new User("zhangsan", 18);
            //user组件依赖了Pet组件
            zhangsan.setPet(tomcatPet());
            return zhangsan;
        }
    
        @Bean("tom22")
        public Pet tomcatPet(){
            return new Pet("tomcat");
        }
    }
    
    public static void main(String[] args) {
            //1、返回我们IOC容器
            ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
    
            //2、查看容器里面的组件
            String[] names = run.getBeanDefinitionNames();
            for (String name : names) {
                System.out.println(name);
            }
    
            boolean tom = run.containsBean("tom");
            System.out.println("容器中Tom组件："+tom);
    
            boolean user01 = run.containsBean("user01");
            System.out.println("容器中user01组件："+user01);
    
            boolean tom22 = run.containsBean("tom22");
            System.out.println("容器中tom22组件："+tom22);
    
    
        }
    ```

- ###### @ImportResource（资源引入）

  - 若文件中resource中存在大量的xml配置的bean标签，则使用该注解进行加入容器， 

  - 在任意一个**配置类**上注解即可

  - ```xml
    ======================beans.xml=========================
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    
        <bean id="haha" class="com.atguigu.boot.bean.User">
            <property name="name" value="zhangsan"></property>
            <property name="age" value="18"></property>
        </bean>
    
        <bean id="hehe" class="com.atguigu.boot.bean.Pet">
            <property name="name" value="tomcat"></property>
        </bean>
    </beans>
    ```

  - ```java
    @ImportResource("classpath:beans.xml")
    @Configuration
    public class MyConfig {}
    
    ======================测试=================
            boolean haha = run.containsBean("haha");
            boolean hehe = run.containsBean("hehe");
            System.out.println("haha："+haha);//true
            System.out.println("hehe："+hehe);//true
    ```

- ###### @ConfigurationProperties（配置绑定）

  - **用于获取application.yml中的值（方式一）**

  -  **@Component + @ConfigurationProperties**

  - ```xml
    mycar.brand=BYD
    mycar.price=1000
    ```

  - ```java
    /**
     * 只有在容器中的组件，才会拥有SpringBoot提供的强大功能
     */
    @Component
    @Data
    @ConfigurationProperties(prefix="mycar") //即可获取到application.yml对应的值
    public class Car {
        
        private String brand;
        private Integer price;
    
    }
    ```

- ###### @EnableConfigurationProperties（自动配置属性注入）

  - **用于获取application.yml中的值（方式二）**

  - **@EnableConfigurationProperties + @ConfigurationProperties（在指定类上指明前缀）**

  - ```java
    @Configuration // 配置类，
    @EnableConfigurationProperties(Car.class)       // 必须在配置类中使用
    //1、开启Car配置绑定功能
    //2、把这个Car这个组件自动注册到容器中
    public class MyConfig {
    }
    ```



### 10.SpringBoot中的切面AOP使用

使用**@Aspect** 指定为切面

使用**@Component** 加入到spring容器中



```java

/**
 * 日志拦截器，使用切面记录为日志形式
 */

@Aspect
@Component
@Slf4j
public class LogAspect {

    // 切点的配置
    @Pointcut("execution(* com.xiaohua.blog.web.*.*(..))")
    public void log(){}


    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURL = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(requestURL,ip,classMethod,args);

        // 记录请求信息
        log.info("Request : {}", requestLog);

    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        // 记录返回信息
        log.info("Result : {} ", result);
    }


    // 内部类，方便封装信息
    @AllArgsConstructor
    @Data
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}

```



### 11.异常处理器

使用**@ControllerAdvice** 指定为异常处理器

使用**@ExceptionHandler** 指定对应的异常类，

```java

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView getException(HttpServletRequest request, Exception e) throws Exception {
        log.error("Request URL : {}, Exception: {}", request.getRequestURL(), e);
        // 用于判断异常类上是否存在状态码判断， 若存在，则抛给springboot框架处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}

```



### 12.拦截器

直接继承**HandlerInterceptor** 即可, 重写其中对应的方法

```java
/**
 * 登录拦截器
 */

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;

    }
}

```

同时增加相应的配置，加入到springMVC容器， 并指定拦截路径
实现 **WebMvcConfigurer** 重写对应的方法

```java

/**
 * 拦截器配置
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}

```










---

## 了解自动配置原理

​	多看几遍，会用之后，回来

​	



### 最佳实践

- 引入场景依赖

- - https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter

- 查看自动配置了哪些（选做）

- - 自己分析，引入场景对应的自动配置一般都生效了
  - 配置文件中debug=true开启自动配置报告。Negative（不生效）\Positive（生效）

- 是否需要修改

- - 参照文档修改配置项

- - - https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties

    - 自己分析。xxxxProperties绑定了配置文件的哪些。

    - 使用

      ```xml
      spring.banner.image.location = classpath:banner.gif/png/jpg 
      可该换启动图标
      ```

- - 自定义加入或者替换组件

- - - @Bean、@Component。。。

- - 自定义器  **XXXXXCustomizer**；
  - ......



### 	Lombok

#### 			常用注解

```java
@Data      			// 注入get,set方法
@ToString   		// 声明toString方法
@AllArgsConstructor	// 全参构造器
@NoArgsConstructor	// 无参构造器
@EqualsAndHashCode	// 重写equals hashcode方法
对于自定义的构造器就自己写
    
@Slf4j  			// 注入log，用于日志
```

​	

### 	Dev-Tools

​	引入依赖

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

​	热部署，修改代码，按下ctrl+F9 重新build project项目



### 	Initailizr 初始化springboot项目



---

---

---



# Springboot核心技术



### 1. yml语法

- 基本语法
  - key: value；kv之间有空格
  - 大小写敏感
  - 使用缩进表示层级关系
  - 缩进不允许使用tab，只允许空格
  - 缩进的空格数不重要，只要相同层级的元素左对齐即可
  - '#'表示注释
  - 字符串无需加引号，如果要加，''与""表示字符串内容 会被 转义/不转义

- 数据类型
  - 



