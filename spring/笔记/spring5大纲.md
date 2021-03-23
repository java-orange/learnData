# Spring5大纲展示



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
* 不修改得情况下,实现增强功能,代码扩展
  * **AOP底层原理**
    * 代理模式, 
    * 分两种: JDK动态代理(有接口),  CGLIB代理(无接口)
    * 动态代理是通过实现接口以增强功能
    * CGLIB代理则是通过类得继承子类以扩展功能
    * JDK动态代理得代码实现,
    * 术语:连接点,切入点,通知(5个),切面
    * 准备:基于AspectJ配合Spring实现
    * 切入点表达式
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
  * 原子性: 不可分割,要么都成功,一个失败都失败
  * 一致性: 操作之前与操作之后的总量是不变的
  * 隔离性: 多事务之间操作不会有影响
  * 持久性: 提交则记录至硬盘中
* 转账事务测试
* 声明式事务管理(底层是AOP)
  * 创建事务管理器<bean/>
  * 开启事务注解
  * 添加注解@transactional
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

* spring5 53集, 尚硅谷, 
* 前置知识: springmvc, springboot, maven, 

​	

