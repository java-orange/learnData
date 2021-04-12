# MyBatisPlus

---

[官方地址](https://mp.baomidou.com/)



## SSM方式整合

### 1.POM文件

```xml
	<!--单元测试依赖-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
        <!--mp依赖
            MybatisPlus 会自动维护Mybatis以及Mybatis-spring相关的依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.1.2</version>
        </dependency>
        <!--log4j-->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <!--c3p0-->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.38</version>
        </dependency>
        <!--spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>4.3.24.RELEASE</version>
        </dependency>

```

### 2.相关的数据表与JavaBean

```sql

-- 创建库 
CREATE DATABASE mp; 
-- 使用库 
USE mp; 
-- 创建表 
CREATE TABLE tbl_employee( 
id INT(11) PRIMARY KEY AUTO_INCREMENT, 
last_name VARCHAR(50), 
email VARCHAR(50), 
gender CHAR(1),
 age int 
 ); 
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('Tom','tom@qq.com',1,22); 
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('Jerry','jerry@qq.com',0,25); 
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('Black','black@qq.com',1,30); 
INSERT INTO tbl_employee(last_name,email,gender,age) VALUES('White','white@qq.com',0,35);

```

```java
@Data
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer age;
    //省略get和set以及ToString方法。
}

```

### 3.Mybatis全局配置文件

mybatis.config.xml

```xml

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configurationPUBLIC "-//mybatis.org//DTD Config 3.0//EN""http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
</configuration>
```

### 4.log4j.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
        <param name="Encoding" value="UTF-8" />
        <layout class="org.apache.log4j.PatternLayout">
            <param   name="ConversionPattern"   value="%-5p   %d{MM-dd HH:mm:ss,SSS} %m  (%F:%L) \n" />
        </layout>
    </appender>
    <logger name="java.sql">
        <level value="debug" />
    </logger>
    <logger name="org.apache.ibatis">
        <level value="info" />
    </logger>
    <root>
        <level value="debug" />
        <appender-ref ref="STDOUT" />
    </root>
</log4j:configuration>
```

### 5.db.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mp
jdbc.username=root
jdbc.password=1234
```

### 6.Spring配置文件，applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 	   xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
       xsi:schemaLocation="http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring-1.2.xsdhttp://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
                           http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">
    <!--数据源-->
    <context:property-placeholder location="classpath:db.properties"/>
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"></property>
        <property name="jdbcUrl" value="${jdbc.url}"></property>
        <property name="user" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>
    <!--事务管理器-->
    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--基于注解的事务管理-->
    <tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>
    <!--配置SqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--数据源-->
        <property name="dataSource" ref="dataSource"></property>
        <property name="configLocation"value="classpath:mybatis-config.xml"></property>
        <!--别名处理-->
        <property name="typeAliasesPackage" value="com.atguigu.mp.beans"></property>
    </bean>
    <!--配置mybatis 扫描mapper接口的路径-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.atguigu.mp.mapper"></property>
    </bean>
</beans>
```

### 7.继承MP

- **Mybatis-Plus 的集成非常简单，对于Spring，我们仅仅需要把Mybatis 自带的MybatisSqlSessionFactoryBean替换为MP 自带的即可。**

```xml
<bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
    <!--数据源-->
    <property name="dataSource" ref="dataSource"></property>
    <property name="configLocation" value="classpath:mybatis-config.xml"></property>
    <!--别名处理-->
    <property name="typeAliasesPackage" value="com.atguigu.mp.beans"></property>
</bean>
```

### 8.通用CRUD

- **基于 Mybatis**
  - 需要编写 EmployeeMapper 接口，并手动编写 CRUD 方法
    提供 EmployeeMapper.xml 映射文件，并手动编写每个方法对应的 SQL 语句.
- **基于 MybatisPlus**
  只需要**创建 EmployeeMapper 接口, 并继承 BaseMapper 接口**.这就是使用 M
  (泛型指定的就是当前Mapper接口所操作的实体类 ) 需要完成的所有操作，甚至不需要创建 SQL 映射文件。
- xxxMapper 继承了 BaseMapper, BaseMapper 中提供了通用的 CRUD 方法, 方法来源于 BaseMapper, 有方法就必须有 SQL, 因为 MyBatis 最终还是需要通过 SQL 语句操作数据

```java
@TableName(value = "tbl_employee")//对应数据库表名
public class Employee {
    /**
     * TableId:
     *  value：指定表中的主键列的列明，如果实体属性名与类名一直，可以省略不指定，
     *  type：指定主键策略
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer age;
    @TableField(exist = false)//忽略数据库没有的值salary
    private Integer salary;
}

```

- #### 常用注解

- **@TableName()** 	 value指定对应的数据库表名

- **@TableId(value,type)**       value指定对应的主键名称， type指定主键类型， 使用IdType的枚举类进行设定。



- 小Tips： 获取插入的Id值
- Mybatis
  - 在**mybatis**的Mapper映射文件中

```xml
<insert id="insert" useGeneratedKeys="true" keyProperty="id"></insert>
```

- 在mybatisPlus中，自动返回插入主键值并填充至JavaBean中

```java
Integer key=employee.getId();
```



#### **C-插入操作**

```java
@Test
public void testCommonInsert() {
        //初始化Employee对象
        Employee employee = new Employee();
        employee.setLastName("MP");
        employee.setEmail("kevin@qq.com");
        Integer result = employeeMapper.insert(employee);
        System.out.println("Result：" + result);
}

```

此时插入结果为：MP,kevin@qq.com，null，null。
**结论**：**insert方法在插入时，会根据实体类的每个属性进行非空判断，只有非空的属性对应的字段才会出现到SQL语句中**

  

#### U-更新操作

```java
	@Test
    public void testCommonUpdate(){
        Employee employee = new Employee();
        employee.setId(6);
        employee.setLastName("MybatisPlus");
        employee.setEmail("MybatisPlus@qq.com");
        employee.setGender(0);
        //可以写一个构造方法，new Employee(6,"Name","email",0,20)
        //如果出现非空会MybatisPlus会进行自动判断，不会出现在SQL语句中
        employee.setAge(20);
        Integer result = employeeMapper.updateById(employee);
        System.out.println("result："+result);
    }
```

**通过ID进行更新操作，传入一个对象会进行自动判断，** **更新非空值。**



## 在MP中，所有的**新增或修改**操作都会进行非空判断，非空则可以进入SQL, 其余值不动



#### S-查询操作

- ##### 通过Id进行查询

```java
	@Test
    public void testCommonSelect(){
        Employee employee = employeeMapper.selectById(3);
        System.out.println("Employee="+employee);
    }
```



- **通过实体查询单条数据**

```java
        Employee employee = new Employee();
        employee.setId(4);
        employee.setLastName("DSM");
        Employee result = employeeMapper.selectOne(employee);
        System.out.println(result);
```

- **传入多个ID查询多条记录**

```java
        List<Integer> idList=new ArrayList<>();
        idList.add(4);
        idList.add(5);
        idList.add(6);
        List<Employee> employees = employeeMapper.selectBatchIds(idList);
        System.out.println(employees);
```

- **使用HashMap封装对象查询**

```java
		Map<String, Object> columnMap=new HashMap<>();
        columnMap.put("last_name","Tom");
        columnMap.put("gender",1);
        List<Employee> result = employeeMapper.selectByMap(columnMap);
        System.out.println(result);

```

- **分页查询**

```java
		IPage<Employee> result = employeeMapper.selectPage(new Page<>(3, 2), null);
        System.out.println(result);
```

查询第三页，每一页显示两条数据，

MybatisPlus会进行自动分页处理，底层使用的Mybatis的RowBounds方法进行处理，内存进行处理，所以SQL是看不到的。

后续会使用MybatisPlus的插件进行物理分页



#### D-删除操作

- **通过id进行删除**

```java
	@Test
    public void testCommonDelete() {
        Integer result = employeeMapper.deleteById(8);
        System.out.println("Result："+result);
    }

```

- **根据条件删除**

```java
		Map<String, Object> columnMap=new HashMap<>();
        columnMap.put("last_name","MP");
        columnMap.put("email","kevin@qq.com");
        Integer result = employeeMapper.deleteByMap(columnMap);
        System.out.println("result"+result);

```

- **批量删除数据**

```java
		List<Integer> idList = new ArrayList<>();
        idList.add(12);
        idList.add(13);
        Integer result = employeeMapper.deleteBatchIds(idList);
        System.out.println("result"+result);

```



### 9.条件构造器



#### 	1.QueryWrapper（条件查询构造器）

```java
public Employee testQueryWrapperSelect(){
		//1.我们需要分页查询tbl_employee表中，年龄在16-50之间性别为男性,姓名为MP的所有用户
        IPage<Employee> employeeIPage = employeeMapper.selectPage(new Page<Employee>(1, 2),
                new QueryWrapper<Employee>()
                        .between("age", 18, 50)
                        .eq("gender", 1)
                        .eq("last_name", "Tom"));
        List<Employee> employees1 = employeeIPage.getRecords();
        employees1.forEach((value)-> System.out.println(value));

        //2.查询tbl_employee表中，性别为女并且名字中带有“Tom”或者邮箱中带有“a“
        List<Employee> employees2 = employeeMapper.selectList(new QueryWrapper<Employee>()
                .eq("gender", 0)
                .like("last_name", "Tom")
                .or()   //SQL:(gender = ? AND last_name LIKE ? OR email LIKE ? )
                //.orNew()  //SQL:(gender = ? AND last_name LIKE ?) OR (email LIKE ? );貌似新版本已经取消orNew()了
                .like("email", "a")
        );
        employees2.forEach(value -> System.out.println(value));

        //3.使用last。查询为女的，根据age进行排序（asc/desc），进行分页
    	// last官方只能使用一次，有sql注入风险, 谨慎使用
        List<Employee> employees3 = employeeMapper.selectList(new QueryWrapper<Employee>()
                .eq("gender", 0)
                .orderByDesc("age")
                // 分页，第一页，每页展示3个
                .last("limit 1,3"));
    		   //.last("limit 1")		只显示一条数据
        employees3.forEach(value -> System.out.println(value));
	}
}

```

#### 2.UpdateWrapper（修改构造器）

```java
@Test
    public void testUpdateWrapperSelect(){
        Employee employee = new Employee();
        employee.setLastName("Jack");
        employee.setEmail("Jack@sina.com");
        employee.setGender(0);
        Integer update = employeeMapper.update(employee, new UpdateWrapper<Employee>()
                        .eq("last_name","Tom")
                        .eq("age",44));
        System.out.println(update);
    }

```



#### 3.UpdateWrapper（删除构造器）

```java
@Test
    public void testDeleteWrapper(){
        Integer delete = employeeMapper.delete(new UpdateWrapper<Employee>()
                .eq("last_name", "Tom")
                .eq("age", 20));
        System.out.println(delete);
    }

```

#### 4.小结

本次教学只是常用的罗列出来，还有很多构造器方法在文档之中，需要自己学
MP：多个Wrapper Condition 条件构造器
Mybatis MBG：xxxExample→Criteria：QBC(Query By Criteria) Hibernate、通用Mapper

---

---



## 新特性.ActiveReccord（活动记录）



#### 1）概述

Active Record(活动记录)，是一种领域模型模式，特点是一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一行记录。
ActiveRecord 一直广受动态语言（ PHP 、 Ruby 等）的喜爱，而 Java 作为准静态语言，对于 ActiveRecord 往往只能感叹其优雅，所以 MP 也在 AR 道路上进行了一定的探索。

#### 1.使用AR：继承Model<实体类>

```java
public class Employee extends Model<Employee> {
	//省略getset方法以及实体类属性
}

```

#### 2.AR插入操作

```java
@Test
    public void testARInsert(){
        Employee employee = new Employee();
        employee.setLastName("Jack");
        employee.setEmail("Jack@qq.com");
        employee.setGender(1);
        employee.setAge(20);
        boolean insert = employee.insert();
        System.out.println("Result="+insert);
    }

```

#### 3.AR修改操作

```java
@Test
    public void testARUpdate(){
        Employee employee = new Employee(16,"JKL","JKL@qq.com",0,15);
        System.out.println(employee.updateById());
    }

```

#### 4.AR删除操作

```java
@Test
    public void testARDelete(){
        Employee employee = new Employee();
        //通过id删除数据
        System.out.println("通过ID删除结果="+employee.deleteById(4));
    }
    	//根据条件删除数据(在逻辑上删除不存在的数据也是返回True)
        System.out.println("通过条件删除结果="+employee.delete(new QueryWrapper<Employee>().eq("last_name","jack")));

```

#### 5.AR查询操作

```java
@Test
    public void testARSelect(){
        Employee employee = new Employee();
        //通过id查询
        System.out.println(employee.selectById(16));
        //查询所有操作
        System.out.println(employee.selectAll());
        //使用Wrapper以及AR进行模糊查询
        employee.selectList(new QueryWrapper<Employee>().like("last_name","J")).forEach(value -> {
            System.out.println(value);
        });
        
    }

```

#### 6.AR分页复杂操作

```java
@Test
    public void testARPage(){
        Employee employee = new Employee();
        //selectpage方法将返回对象封装到一个Page对象中，如果要得到需要使用getRecord()方法。
        IPage<Employee> employeeIPage = employee.selectPage(new Page<>(1, 1), new QueryWrapper<Employee>().like("last_name", "T"));
        employeeIPage.getRecords().forEach(value -> {
            System.out.println(value);
        });
    }

```

#### 7.小结

AR 模式提供了一种更加便捷的方式实现 CRUD 操作，其本质还是调用的 Mybatis 对应的方法，类似于**语法糖**。指计算机语言中添加的某种语法，这种语法对原本语言的功能并没有影响。可以更方便开发者使用，可以避免出错的机会，让程序可读性更好。

---

---



## 关于代码生成器



### 1.依赖

添加 模板引擎 依赖和日志依赖，MyBatis-Plus 支持 **Velocity**（默认）、**Freemarker**、**Beetl**，用户可以选择自己熟悉的模板引擎，如果都不满足要求，可以采用自定义模板引擎。

```xml
<!--Apach Velocity模版依赖-->
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity</artifactId>
            <version>1.7</version>
        </dependency>
<!--sfl4j日志依赖-->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.7</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.7</version>
        </dependency>

```

### 2.代码生成器

```java
@Test
    public void testGenerator() {
        //1.全局策略配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)//是否支持AR模式
                .setAuthor("kevin")//作者
                .setOutputDir("D:\\Spring\\Spring-couse\\MyBatisPlus03\\src\\main\\java")//输出路径
                .setFileOverride(true)//文件覆盖
                .setIdType(IdType.AUTO)//主键策略
                .setServiceName("%sService")//设置生成service接口名字的首字母是否为I（默认会生成I开头的IStudentService）
                .setBaseResultMap(true)//自动SQL映射文件，生成基本的ResultMap
                .setBaseColumnList(true);//生成基本的SQL片段

        //2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)//设置数据库类型
                .setDriverName("com.mysql.jdbc.Driver")//数据库驱动名
                .setUrl("jdbc:mysql://localhost:3306/mp?useUnicode=true&characterEncoding=UTF-8")//数据库地址
                .setUsername("root")//数据库名字
                .setPassword("123");//数据库密码

        //3.策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true)//全局大写命名
                .setDbColumnUnderline(true)//指定表明 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel)//数据库表映射到实体的命名策略
                .setTablePrefix("tbl_")//前置命名
                .setInclude("tbl_employee");//生成的表

        //4.包名策略
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("org.example")//所放置的包
                .setMapper("mapper")//Mapper包
                .setService("service")//服务层包
                .setController("controller")//控制层
                .setEntity("beans")//实体类
                .setXml("mapper");//映射文件
        //5.整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategy)
                .setPackageInfo(packageConfig);
        //6.执行
        autoGenerator.execute();
    }

```

### 3.关于服务层自动注入

Service层的接口自动继承了了IService接口，里面有一些常用操作的接口
Service层的实现类不仅实现了Service层的接口，并且还继承了ServiceImpl类，该类也实现了IService接口。所以我们的serviceimpl不需要写一些简单具体操作的实现。并且也不需要自动注入mapper，在ServiceImpl中已经帮我们注入了。

```java
//服务层接口
public interface EmployeeService extends IService<Employee> {

}
//服务层实现类
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    //不用再进行Mapper的注入
    /**
     * EmployeeServiceImpl  集成了ServiceImpl
     * 1.在ServiceImpl中已经完成Mapper对象的注入，直接在EmployeeServiceImpl中进行使用
     * 2.在ServiceImpl中野帮我们提供了常用的CRUD方法，基本的一些CRUD方法在Service中不需要我们自己定义
     */
}
//被继承的接口
public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {
    @Autowired
    protected M baseMapper;//已自动注入Mapper
    //各种方法省略
}

```





## SpringBoot整合MybatisPlus

---

##### application.yml

```yml
server:
  port: 8085
  servlet:
    context-path: /test


spring:
  #redis集群
  redis:
    host: 127.0.0.1
    port: 6379
    timeout: 20000
    #    集群环境打开下面注释，单机不需要打开
    #    cluster:
    #      集群信息
    #      nodes: xxx.xxx.xxx.xxx:xxxx,xxx.xxx.xxx.xxx:xxxx,xxx.xxx.xxx.xxx:xxxx
    #      #默认值是5 一般当此值设置过大时，容易报：Too many Cluster redirections
    #      maxRedirects: 3
    password: lyja
    application:
      name: test
    jedis:
      pool:
        max-active: 8
        min-idle: 0
        max-idle: 8
        max-wait: -1
    database: 0

  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
  datasource:
    dynamic:
      #设置默认的数据源或者数据源组,默认值即为master
      primary: master
      strict: false
      datasource:
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false
          username: root
          password: lyja
    # 数据源配置
    druid:
      # druid连接池监控
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        login-username: admin
        login-password: admin
        # 初始化时建立物理连接的个数
        initial-size: 5
        # 最大连接池数量
        max-active: 30
        # 最小连接池数量
        min-idle: 5
        # 获取连接时最大等待时间，单位毫秒
        max-wait: 60000
        # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        time-between-eviction-runs-millis: 60000
        # 连接保持空闲而不被驱逐的最小时间
        min-evictable-idle-time-millis: 300000
        # 用来检测连接是否有效的sql，要求是一个查询语句
        validation-query: select count(*) from dual
        # 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        test-while-idle: true
        # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        test-on-borrow: false
        # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        test-on-return: false
        # 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        pool-prepared-statements: false
        # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
        max-pool-prepared-statement-per-connection-size: 50
        # 配置监控统计拦截的filters，去掉后监控界面sql无法统计
        filters: stat,wall
        # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        connection-properties:
          druid.stat.mergeSql: true
          druid.stat.slowSqlMillis: 500
        # 合并多个DruidDataSource的监控数据
        use-global-data-source-stat: true
        filter:
          stat:
            log-slow-sql: true
            slow-sql-millis: 1000
            merge-sql: true
          wall:
            config:
              multi-statement-allow: true
  servlet:
    multipart:
      # 开启 multipart 上传功能
      enabled: true
      # 文件写入磁盘的阈值
      file-size-threshold: 2KB
      # 最大文件大小
      max-file-size: 200MB
      # 最大请求大小
      max-request-size: 215MB

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    auto-mapping-behavior: full
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath*:mapper/**/*Mapper.xml
  global-config:
    # 逻辑删除配置
    db-config:
      # 删除前
      logic-not-delete-value: 1
      # 删除后
      logic-delete-value: 0
logging:
  level:
    root: info
    com.example: debug
```



