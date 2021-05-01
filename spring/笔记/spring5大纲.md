# Spring5大纲展示



##### 尚硅谷的**王泽老师一定不能看**，太机械死板，就是硬背api，避之矣。



#### 		spring的入门展示 hello world

*  引入 xml 文件, bean标签, ApplicationContext类

  

## 1.1SpringIOC

* 控制反转, 原理 -> 见图1,图2

  ### 	 SpringIOC的bean管理

* 注入对象

* 注入属性

* bean管理两种方式: xml文件, @注解

  

  **Spring 的DI依赖注入**

  * 两种注入方式: set()方法, generator()方法
  * set注入使用property
  * 构造方法使用contructor-arg
  * 注入对象
  * 注入集合,数组
  * 提取集合,数组
  * 两种bean管理方式, 普通bean, 工厂FactoryBean
  * bean的作用域, scrop属性, singleton. prototype
  * bean的生命周期
  * bean的自动装配 autowire   byName, byType
  * 外部属性文件的导入, druid连接池
  * 基于注解管理bean, 依赖, 组件扫描, 注解
  * 组件扫描细节配置
  * 完全注解开发

## 1.2 SpringAOP

* AOP编程, 即面向切面编程
* 不修改的情况下,实现增强功能,代码扩展
  * **AOP底层原理**
    * 代理模式, 
    
    * 分两种: JDK动态代理(有接口),  CGLIB代理(无接口)
    
    * 动态代理是通过实现接口以增强功能
    
    * CGLIB代理则是通过类得继承子类以扩展功能
    
    * JDK动态代理得代码实现,
    
    * 术语:连接点,切入点,通知(5个),切面
    
    * 准备:**基于AspectJ配合Spring实现**
    
      ## 在spring容器中进行扫描该注解所在的包
    
      
    
    * 切入点表达式
    
    * **@componet, @Aspect** 增强类上的注解
    
    * 开启生成代理对象
    
      ```xml
      <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
      ```
    
    - 切入点抽取:
    
      ```java
      //相同切入点抽取
      @Pointcut(value = "execution(* com.atguigu.spring5.aopanno.User.add(..))")
      public void pointdemo() {}
      
      // 引入便只需
      //@Before注解表示作为前置通知
      @Before(value = "pointdemo()")
      public void before() {
          System.out.println("before.........");
      }
      ```
    
    
    1. 在spring.xml 中  扫描aop切面所在的包
    2. 在spring.xml 中  开启aspectj代理生成对象
    3. 在该类上面添加@component, @aspectj 注解
    4. 在该类上进行切入点抽取
    5. 在该类的方法上添加不同的通知注解
    
    
    
    * 两种实现AOP方式, xml格式(**了解**)与注解模式(**重点**)
      *  before(前置通知),
      * afterReturning(后置通知),
      * after(最终通知),
      * afterThrowing(异常通知),
      * around(环绕通知) -> 参数ProceedingJoinPoing
    * 切入点抽取
    * 多个增强类的优先级配置 @order(1)

## 1.3 JdbcTemplate

* 相关配置,druid连接池,jdbcTemplate对象,组件扫描,注入对象

* jdbcTemplate入门案例
  * 增删改                               update
  * 查询返回某个值                queryForObject
  * 查询返回对象                    queryForObject
  * 查询返回集合                     query
  
* jdbcTemplate批量操作数据库(**CRUD**)                 ->**batchUpdate**

* 事务相关ACID
  * **原子性: 不可分割,要么都成功,一个失败都失败**
  * **一致性: 操作之前与操作之后的总量是不变的**
  * **隔离性: 多事务之间操作不会有影响**
  * **持久性: 提交则记录至硬盘中**
  
* 转账事务测试

* 声明式事务管理(底层是AOP)

  *  配置事务管理器 

    ```xml
    <!--创建事务管理器-->
    <bean id="transactionManager"  class="org.springframework.jdbc.datasource.DataSourceTransactionManager"><!--注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    ```

    

  * ```xml
    （2）开启事务注解
    <!--开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
    ```

  * 开启事务注解

  * 添加注解@transactional, 再@service层

* 声明式事务管理参数配置
  * 事务传播行为
  * 事务隔离级别
  * 超时时间
  
* xml格式配置事务(**了解**)

* 完全注解开发

## 1.4 Spring5新功能

* 整合log4j2日志框架
* 整合junit4测试
* 整合junit5测试

## 1.5 SpringWebFlux

* webFlux 异步非阻塞方式，响应式编程，异步非阻塞的框架在Servlet3.1以后才支持，核心是基于Reactor的相关API实现的。
* 基于netty得nio通信方式
* **异步和同步针对调用者**，调用者发送请求，如果等着对方回应之后才去做其他事情就是同步，如果发送请求之后不等着对方回应就去做其他事情就是异步
* **阻塞和非阻塞针对被调用者**，被调用者受到请求之后，做完请求任务之后才给出反馈就是阻塞，受到请求之后马上给出反馈然后再去做事情就是非阻塞

### **Webflux特点**：

- **非阻塞式**：在有限资源下，提高系统吞吐量和伸缩性，以Reactor为基础实现响应式编程

- **函数式编程**：Spring5框架基于java8，Webflux使用Java8函数式编程方式实现路由请求

### **什么是响应式编程**

​	响应式编程是一种面向**数据流**和变化传播的编程范式。这意味着可以在编程语言中很方便地表达静态或动态的数据流，而相关的计算模型会自动将变化的值通过数据流进行传播。

​	电子表格程序就是响应式编程的一个例子。单元格可以包含字面值或类似"=B1+C1"的公式，而包含公式的单元格的值会依据其他单元格的值的变化而变化。

​	底层是**观察者模式**，在Java8中提供的观察者模式两个类Observer和Observable

```java
public class ObserverDemo extends Observable {
    public static void main(String[] args) {
        ObserverDemo observer = new ObserverDemo();
        
        // 添加观察者
        observer.addObserver((o,arg) -> {
            System.out.println("发生变化");
        });
        observer.addObserver((o,arg) -> {
             System.out.println("变化被观察者通知，准备变化");
        });
        
        observer.setChanged(); // 数据变化
        observer.notifyObservers();  // 发送通知
        
    }
}
```



### **响应式编程（Reactor实现）**

​	**就是发布者与订阅者之间的数据流通信**

（1）响应式编程操作中，Reactor是满足Reactive规范框架

（2）Reactor有两个核心类，**Mono**和**Flux**，这两个类实现接口**Publisher**，提供丰富操作符。

- **Flux**对象实现发布者，返回N个元素；
- **Mono**实现发布者，返回0或者1个元素

```java
public static void main(String[] args) {
    //just方法直接声明，添加多个元素
    Flux.just(1,2,3,4);
	// 添加一个元素
    Mono.just(1);
    //其他的方法
    Integer[] array = {1,2,3,4};
    // 添加数组
    Flux.fromArray(array);
    List<Integer> list = Arrays.asList(array);
    // 添加集合
    Flux.fromIterable(list);
    Stream<Integer> stream = list.stream();
    // 添加流
    Flux.fromStream(stream);
    
    //-----------------------------------------------------
    // 这样才能将数据发射，不然没有订阅者，消息无用
    Flux.just(1,2,3,4).subscribe(System.out::println);
    Mono.just(1).subscribe(System.out.println);
    
    
}
```



发送信号有三种：**元素值**，**错误信号**，**完成信号**

错误信号和完成信号都是终止信号，不能共存的，错误信号和完成信号都代表终止信号，终止信号用于告诉订阅者数据流结束了，错误信号终止数据流同时把错误信息传递给订阅者

如果没有发送任何元素值，而是直接发送错误或者完成信号，表示是空数据流

如果没有错误信号，没有完成信号，表示是无限数据流



**对于其中的操作符**

- map 操作符
  - 将元素映射为新元素，加工处理
- flatMap 操作符
  - 将元素映射为流，把每个元素转换成流，把转换之后多个流合并大的流



### **SpringWebflux执行流程和核心API**

1）SpringWebflux基于Reactor，默认使用容器是Netty，Netty是高性能的NIO框架，异步非阻塞的框架

2）SpringWebflux执行过程和SpringMVC相似的

 SpringWebflux核心控制器**DispatchHandler**，实现接口**WebHandler**接口WebHandler有一个方法

![](\分析图\webhandler.png)

对于其子类，**DispatchHandler** 实现该方法

![](分析图\dispatachhandler.png)

**执行流程与springMVC大体相似**

SpringWebflux里面DispatcherHandler，负责请求的处理

 HandlerMapping：请求查询到处理的方法

HandlerAdapter：真正负责请求处理

HandlerResultHandler：响应结果处理

（4）SpringWebflux实现函数式编程，两个接口：

- RouterFunction（路由处理）
- HandlerFunction（处理函数）



---

### **小demo 基于注解开发webflux应用**



基于springboot开发

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

**实体类**

```java
@Data
@AllargsConstructor
public class User{
    private String name;
    private String gender;
    private Integer age;
}
```



**Service层**

```java
public interface UserService {
    Mono<User> getUserById(Integer id);
    
    Flux<User> getAllUser();
    
    Mono<Void> saveUserInfo(Mono<User> user);
    
}
```

```java
@service
public class UserServiceImpl implements UserService {
    
    // 就不连接数据库了，用map做代替了
    private final Map<Integer,User> users = new HashMap<>;
    
    public UserServiceImpl() {
        this.users.put(1,new User("lucy","男",23));
        this.users.put(2,new User("peter","男",14));
        this.users.put(3,new User("jack","女",27));
    }
    
	// 根据主键查询，可能为空，最多一个
    public Mono<User> getUserById(Integer id) {
        return Mono.justOrEmpty(this.users.get(id));
    }
    
    // 返回所有map值
    public Flux<User> getAllUSer() {
        return Flux.fromIterable(this.users.valus());
    }

    
    // 添加用户
    public Mono<User> saveUserInfo(Mono<User> userMono) {
        // 从Mono中取出数据
        return userMono.doOnNext(user -> {
            int id = users.size() + 1;
            users.put(id,user);
        }).thenEmpty(Mono.empty());	//返回结束信息，空值
    }
    
}
```

**controller层**

```java
public class UserController {
    @Autowired
    private UserService userservice;
    
    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVaiable("id") Integer id) {
        return userservice.getUserById(id);
    }
    
    @GetMapping("/users")
    public Flux<User> getAllUSers() {
        return userservice.getAllUser();
    }

    @PostMapping("/saveUser")
    public Mono<Void> saveUser(@ResponseBody User user) {
        // 将对象放入Mono中
        Mono<User> userMono = Mono.just(user);
        return userserivce.saveUserInfo(userMono);
        
    }
}
```



外部在进行调用时，并无任何不同，但是实现原理却大大改变。



---

---

---

---

---





## SpringWebFlux的函数式编程

使用springboot的webFlux编程，则依赖其自动配置会配置Netty容器，

若使用函数式编程，要实现两个关键的接口

- RouterFunction（实现路由功能，请求转发给对应的handler）
- HandlerFunction（处理请求生成响应的函数）。

核心任务定义两个函数式接口的实现并且启动需要的服务器。

SpringWebflux请 求 和 响 应 不 再 是ServletRequest和ServletResponse，而是ServerRequest和ServerResponse



其中的User实体类，Service,ServiceImpl 均完全一致。

controller层废弃，

#### 自写Handler

```java
public class UserHandler {
    private final UserService userService;
    public UserHandler(UserService userService) {
		this.userService = userService;
    }
    //根据id查询
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        //获取id值
        int userId = Integer.valueOf(request.pathVariable("id"));
        //空值处理
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        //调用service方法得到数据
        Mono<User> userMono = this.userService.getUserById(userId);
        //把userMono进行转换返回
        //使用Reactor操作符
        flatMapreturnuserMono.flatMap(person -> 	ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(person))).switchIfEmpty(notFound);
    }
    
    //查询所有
    public Mono<ServerResponse> getAllUsers() {
        //调用service得到结果
        Flux<User> users = this.userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users,User.class);
    }
    //添加
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        //得到user对象
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.saveUserInfo(userMono));
    }
}

```

#### 初始化服务器，编写Router   

创建路由的方法

```java
//1 创建Router路由
public RouterFunction<ServerResponse> routingFunction() {
    //创建hanler对象
    UserService userService = new UserServiceImpl();
    UserHandler handler = new UserHandler(userService);
    //设置路由
    return RouterFunctions.route(GET("/users/{id}").and(accept(APPLICATION_JSON)),handler::getUserById).andRoute(GET("/users").and(accept(APPLICATION_JSON)),handler::getAllUsers);
}

//   创建服务器完成适配
    //2 创建服务器完成适配
public void createReactorServer() {
    //路由和handler适配
    RouterFunction<ServerResponse> route = routingFunction();
    HttpHandler httpHandler = toHttpHandler(route);
    ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
    //创建服务器
    HttpServer httpServer = HttpServer.create();
    httpServer.handle(adapter).bindNow();
}
// 最终调用
    public static void main(String[] args) throws Exception{
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }
```



此时，启用main方法，（不借助springboot的启动器），控制台打印端口，利用端口映射对应路径，则有响应的响应。



#### 对于使用WebClient调用

```java
public class Client {
    public static void main(String[] args) {
        //调用服务器地址
        WebClient webClient = WebClient.create("http://127.0.0.1:5794");
        //根据id查询
        String id = "1";
        User userresult = webClient.get().uri("/users/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(User.class).block();
        System.out.println(userresult.getName());
        
        //查询所有
        Flux<User> results = webClient.get().uri("/users").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(User.class);
        results.map(stu -> stu.getName()).buffer().doOnNext(System.out::println).blockFirst();
    }
}
```



此意为在方法中调用。



webFlux学的一塌糊涂

