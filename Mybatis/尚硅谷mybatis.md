# 尚硅谷Mybatis大纲

[尚硅谷](https://www.bilibili.com/video/BV1mW411M737)



## Mybatis 的 hello world

 - 获取sqlSessionFactory

   ```java
   String resource = "mybatis-config.xml";
   InputStream is = Resources.getResourceAsStream(resource);
   SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
   ```

 - 根据sqlSessionFactory获取sqlSession

   ```java
   SqlSession sqlSession = sqlSessionFactory.openSession();
   ```

 - 根据sqlSession.getMapper() 获取接口的实现类

   ```java
   EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.xml);
   ```

 - 执行接口方法

   ```java
   Employee employee = mapper.getEmpById(2);
   ```

- **mybatis-config.xml文件与Employee的类绑定,且其中的标签id与方法名绑定, 则mybatis因此创造代理对象**

- **mybatis-config.xml**

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTDConfig3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"></transactionManager>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/bjsxt"/>
                  <property name="username" value="root"/>
                  <property name="password" value="root"/>
              </dataSource>
          </environment>
      </environments>
  
      <mappers>
          <package name="com.atguigu.mapper"/>   包名,class名用点,resource用/
  <!--        <mapper class="com.atguigu.mapper.EmployeeMapper"/>-->
  <!--        <mapper resource="com/atguigu/mapper/EmployeeMapper.xml" />-->
      </mappers>
  
  </configuration>
  ```

  因EmployeeMapper.xml文件位于java包下, 所以在pom文件中增加资源

  ```xml
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
      </build>
  
  ```

* **EmployeeMapper.xml**

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
  <mapper namespace="com.atguigu.mapper.EmployeeMapper">
      <select id="getEmpById" resultType="com.atguigu.pojo.Employee">
          select id , last_name lastName, email, gender from tbl_employee where id = #{id}
      </select>
  
  </mapper>
  ```

* 日志相关配置文件 **log4j.properyties**

  ```properties
  #log4j.rootLogger=debug,console,logfile
  log4j.rootLogger=debug,console
  
  ### appender.console输出到控制台 ###
  log4j.appender.console=org.apache.log4j.ConsoleAppender
  log4j.appender.console.layout=org.apache.log4j.PatternLayout
  log4j.appender.console.layout.ConversionPattern=<%d> %5p (%F:%L) [%t] (%c) - %m%n
  log4j.appender.console.Target=System.out
  
  ### appender.logfile输出到日志文件 ###
  log4j.appender.logfile=org.apache.log4j.RollingFileAppender
  log4j.appender.logfile.File=SysLog.log
  log4j.appender.logfile.MaxFileSize=500KB
  log4j.appender.logfile.MaxBackupIndex=7
  log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
  log4j.appender.logfile.layout.ConversionPattern=<%d> %p (%F:%L) [%t] %c - %m%n
  
  ```

* 测试用例

  ```java
   @Test
      public void test() throws IOException {
          String resource = "mybatis-config.xml";
          InputStream is = Resources.getResourceAsStream(resource);
          SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
  
          SqlSession sqlSession = null;
          try {
              sqlSession = sqlSessionFactory.openSession();
  
              EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
              Employee emp = mapper.getEmpById(1);
              System.out.println(emp);
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              sqlSession.close();
          }
  
      }
  ```

## 关于各个对象相关的生命周期及使用方式

### SqlSessionFactoryBuilder

​		该类用来创建SqlSessionFactory对象，当SqlSessionFactory对象被创建后，SqlSessionFactoryBuilder就失去了作用，所以它只能存在于创建SqlSessionFactory的方法中，而不要让其长期存在。因此**SqlSessionFactoryBuilder实例的最佳作用域是方法作用域**。

### SqlSessionFactory

​		SqlSessionFactory的生命周期存在于整个MyBatis的应用之中，所以一旦创建了SqlSessionFactory，就要长期保存它，直至不再使用MyBatis应用，所以可以认为SqlSessionFactory的生命周期就等同于MyBatis的应用周期。由于SqlSessionFactory是一个对数据库的连接池，所以它占据着数据库的连接资源。如果创建多个SqlSessionFactory，那么就存在多个数据库连接池，这样不利于对数据库资源的控制，也会导致数据库连接资源被消耗光，出现系统宕机等情况，所以尽量避免发生这样的情况。**因此SqlSessionFactory是一个单例，让它在应用中被共享。**

### SqlSession

​		SqlSession应该存活在一个业务请求中，处理完整个请求后，应该关闭这条连接，让它归还给SqlSessionFactory，否则数据库资源就很快被耗费精光，系统就会瘫痪，所以用try...catch...finally...语句来保证其正确关闭。所以**SqlSession的最佳的作用域是请求或方法作用域。**

​		SqlSession非线程安全,每次都应该获取新的对象

### Mapper

​		由于SqlSession的关闭，它的数据库连接资源也会消失，所以它的生命周期应该小于等于SqlSession的生命周期。Mapper代表的是一个请求中的业务处理，所以它**应该在一个请求中，一旦处理完了相关的业务，就应该废弃它**



## Mybatis-config.xml 全局配置文件

**配置文件的顺序不能改变, 按顺序写, 没有就不写,不能调换位置**

### Properties 引入标签

> 引入外部properties配置文件的内容
>
> ```xml
> <!--    引入properties 文件-->
>  <properties resource="db.properties" ></properties>
>    ```
> 
>

### Settings 全局设置标签

> **mapUnderscoreToCamelCaseEnables  驼峰命名法**
>
> ```xml
>     <settings>
> <!--        开启驼峰命名-->
>         <setting name="mapUnderscoreToCamelCase" value="ture"/>
>     </settings>
> ```
>
> 

### typeAliases 别名标签

> ```xml
> <!--    别名处理器-->
>     <typeAliases>
> <!--        为某一个Java类型起别名, 默认是类名的小写, 可以使用alias起不同别名-->
> <!--        <typeAlias type="com.atguigu.pojo.Employee" alias="emp"/>-->
> <!--        <typeAlias type="com.atguigu.pojo.Employee" />-->
> <!--        为某个包批量起别名-->
>         <package name="com.atguigu.pojo"/>
>     </typeAliases>
> ```
>
> **别名不区分大小写**
>
> 还可以在bean类上使用@Alias注解为类起别名

### typeHandlers 标签类型处理器

> 主要作用是将java属性类性转为数据库的基本类型配置, 默认可用

### plugins 插件标签

> 插件,

### environments 环境标签

> 可配置多个标签,  使用default 指定默认环境

### databaseIdProvider 执行不同的sql根据不同的数据库厂商

> [极其详细的配置](https://www.bilibili.com/video/BV1mW411M737?p=13&spm_id_from=pageDriver)

### mappers 指定sql映射文件

> ```xml
> <mappers>
>    <!--批量注册,必须将该接口与xml文件放在同一个包之下 -->
>      <package name="com.atguigu.mapper"/>
> <!-- 注册接口,方式一:  必须将该接口与xml文件放在同一个包之下 
>     方式二(不推荐):  直接写接口, 不要xml配置文件, 在接口方法之上使用@select,@insert,@delete等方式写入sql语句	-->
>     使用class属性是写接口。 
> <!--        <mapper class="com.atguigu.mapper.EmployeeMapper"/>-->
> <!--        <mapper resource="com/atguigu/mapper/EmployeeMapper.xml" />-->
>  </mappers>
> ```



## 映射文件

### CRUD

对于增删改, 使用sqlSessionFactory.openSession() 默认是手动提交事务, 需 sqlSession.commit() 才可提交

可以使用sqlSessinFactory.openSession(true) 来直接打开事务的自动提交



**mysql支持自增主键**

 **userGeneratedKeys = "true"**	 	**开启主键回填**

 **keyProperty="id"**							  **主键回填的位置**

```xml
<insert id="addEmp" useGeneratedKeys="true" keyProperty="id">
        insert into tbl_employee(last_name,email, gender) values (#{lastName}, #{email}, #{gender})
</insert>
```



#### 对于oracle 数据, 不支持主键自增, 想要获取主键

[oracle回填主键](https://www.bilibili.com/video/BV1mW411M737?p=19&spm_id_from=pageDriver);

[前置知识]()  -> 向上学习 

> databaseIdProvider 执行不同的sql根据不同的数据库厂商

> [极其详细的配置]()



### Mybatis 参数处理

> > 单个参数无所谓, 
>
> >  **多个参数,** mybatis底层会将其包装成一个map , 使用时需要#{arg0} 或者 #{param1} ,
> >
> > 要么 [  arg0 ~ arg(n-1) ]  
> >
> > 要么 [ param1 ~ param(n) ] 
>
> 
>
> ```xml
> <select id="selectEmpByNameAndGender" resultType="employee">
> --         select * from tbl_employee where last_name = #{arg0} and gender = #{arg1}
>         select * from tbl_employee where last_name = #{param1} and gender = #{param2}
>     </select>
> 
> ```
>
> ```java
> // 接口
>     Employee selectEmpByNameAndGender(String name, Character gender);
> 
> // 测试
>  sqlSession = sqlSessionFactory.openSession(true);
>             EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
>             Employee employee = mapper.selectEmpByNameAndGender("123", '1');
>             System.out.println(employee);
> 
> ```
>
> >  **命名参数(强烈推荐使用)**:  @param() 指定参数名, 方便取出 
>
> ```xml
>   <select id="selectEmpByNameAndGender" resultType="employee">
>         select * from tbl_employee where last_name = #{name} and gender = #{gender}
> 
>     </select>
> ```
>
> ```java
>     Employee selectEmpByNameAndGender(@Param("name") String name, @Param("gender") Character gender);
> 
> ```
>
> >  **如果多个参数可以被包装为pojo对象, 则直接使用对象的属性名即可**
>
>  
>
> >  **对于Map参数**, 则使用map的key取出即可 #{key}
>
>  
>
> > 对于**List参数**, 单个list传输过来, 则只能使用#{list[1]} 来取出对应的数据
>
>  
>
> > 对于**array数组**参数, 单个数组传输过来, 则只能使用#{array[0]} 来取出对应的数据
>
>  
>
> 
>
> **#{} 与 ${} 的区别**
>
> >  #{}: 会进行预编译, ? 占位符
> >
> >  ${}: 字符串拼接, 直接拼接, 会产生sql注入问题
>
>  
>
> **对于表名, 或者排序的desc, asc 这种不支持预编译的sql, 使用${}进行拼接**
>
> 类如: select * from ${table_name} 
>
> ​		select * from employee order by ${param} ${order}
>
> ​		可扩展为 select * from employee order by id desc





## 返回值

>  对于要**返回list集合**, 则指定 resultType为集合内元素类型即可
>
> ```xml
> <select id="getEmpAll" resultType="com.atguigu.pojo.Employee">
>         select * from tbl_employee
>     </select>
> ```
>
> ```java
>    sqlSession = sqlSessionFactory.openSession(true);
>             EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
>             List<Employee> empAll = mapper.getEmpAll();
>             empAll.forEach(employee -> System.out.println(employee));
> ```
>
>  
>
> **对于返回对象, 但想要包装为map集合**
>
> ```xml
>   <select id="getEmpToMap" resultType="java.util.Map">
>         select * from tbl_employee where id = #{id}
>     </select>
> ```
>
> ```java
>  sqlSession = sqlSessionFactory.openSession(true);
>             EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
>             Map<String, Object> map = mapper.getEmpToMap(1);
>             Set<String> strings = map.keySet();
>             for (String string : strings) {
>                 System.out.println(string + "-->  " + map.get(string));
>             }
> ```
>
>  
>
>  **对于多条记录封装为map集合**
>
>  ```xml
>     <select id="getEmpByLikeName" resultType="java.util.Map">
>         select * from tbl_employee where last_name like #{name};
>     </select>
>  ```
>
> ```java
>     @MapKey("id")       // 指定map的key
>     Map<Integer, Employee> getEmpByLikeName(String name);
> 
> ```
>
>  





### resultMap 自定义结果集

> **resultMap 自定义结果集**, id唯一标识, type: 封装的pojo类
>
> **id : 指定主键所在的列**
>
> **result: 指定其余列**
>
> **colunm: 指定数据库的字段名**
>
> **property: 指定pojo类中的属性名**
>
> 

> ```xml
>   <resultMap id="myMap" type="com.atguigu.pojo.Employee">
>       
>         <id column="id" property="id" />
>         <result column="email" property="email" />
>         <result column="last_name" property="lastName"/>
>         <result column="gender" property="gender" />
>     </resultMap>
>     <select id="getEmpById" resultMap="myMap">
>         select * from tbl_employee where id = #{id};
>     </select>
> ```
>
>  
>
>  **关于多表查询的环境搭建**
>
> ```sql
> # 创建部门表
> create table tbl_dept(
>  id int(11) primary key auto_increment,
>  dept_name varchar(200)
> )
> 
> #修改员工表, 添加字段
> alter table tbl_employee add column d_id int(11);
> 
> # 添加外键, 用以关联两边
> alter table tbl_employee add constraint fk_emp_dept foreign key(d_id) references tbl_dept(id)
> 
> ```
>
>  
>
>  
>
>  **查询一个员工, 附带其部门信息, 多表查询**
>
> ```xml
>  <resultMap id="myDifMap" type="com.atguigu.pojo.Employee">
>         <id column="id" property="id" />
>         <result column="email" property="email" />
>         <result column="last_name" property="lastName"/>
>         <result column="gender" property="gender" />
>         <result column="did" property="dept.id" />
>         <result column="dept_name" property="dept.deptName" />
>     </resultMap>
>     <select id="getEmpAndDept" resultMap="myDifMap">
>         select e.id id, e.last_name last_name, e.email email, e.gender gender, d.id did , d.dept_name dept_name
> 	 from tbl_employee e, tbl_dept d
> 	 where e.id = d.id and e.id = #{id}
>     </select>
> ```
>
> ```java
> @Data
> public class Employee {
>     private Integer id;
>     private String lastName;
>     private Character gender;
>     private String email;
>     private Dept dept;
> }
> 
> ```
>
>  

### association 联合属性

>  用于指定联合的javabean对象
>
>  **property 指定属性**
>
>  **javaType 指定对象**
>
>  ```xml
>  <resultMap id="myDifMap2" type="com.atguigu.pojo.Employee">
>       <id column="id" property="id" />
>       <result column="email" property="email" />
>       <result column="last_name" property="lastName"/>
>       <result column="gender" property="gender" />
>       <association property="dept" javaType="com.atguigu.pojo.Dept" >
>           <id column="did" property="id" />
>           <result column="dept_name" property="deptName" />
>       </association>
>   </resultMap>
>  
>  
>   <select id="getEmpAndDept" resultMap="myDifMap2">
>       select e.id id, e.last_name last_name, e.email email, e.gender gender, d.id did , d.dept_name dept_name
>  from tbl_employee e, tbl_dept d
>  where e.id = d.id and e.id = #{id}
>   </select>
>  ```
>
>   
>
>   多表查询, 直接的多表 其内部就是inner join on 的简化版
>
>  ```sql
>  select * from employee, department where employee.d_id = department.id and employee.id = 1;
>  select * from employee inner join department on employee.d_id = department.id where employee.id = 1;
>  ```
>
>  
>
>  **使用association 进行分步查询**
>
>  **EmpMapper.xml**
>
>  ```xml
>  <resultMap id="stepSelect" type="com.atguigu.pojo.Employee">
>       <id column="id" property="id"/>
>       <result column="last_name" property="lastName"/>
>       <result column="email" property="email" />
>       <result column="last_name" property="lastName"/>
>       <result column="gender" property="gender" />
>  <!--        分步查询, 先查询employee 表,
>           再根据association联系
>           property: 指定dept关联
>           select: mapper.xml的唯一标记,
>           column: 指定传输的参数,是由d_id传输
>      流程: 将select 指定的方法, 将column 指定的参数传入,并封装给property属性
>  -->
>       <association property="dept"
>                    select="com.atguigu.mapper.DeptMapper.getDeptByStep"
>                    column="d_id" />
>   </resultMap>
>  
>   <select id="getEmpByStep" resultMap="stepSelect">
>       select * from tbl_employee where id = #{id};
>   </select>
>  ```
>
>  **DeptMapper.xml**
>
>  ```xml
>  <mapper namespace="com.atguigu.mapper.DeptMapper">
>  
>   <select id="getDeptByStep" resultType="com.atguigu.pojo.Dept">
>       select * from tbl_dept where id = #{id};
>   </select>
>  
>  ```
>
>  
>
>  
>
>  **在使用association 分步加载时,  可配置延迟加载**
>
>  **mybatis-config.xml**
>
>  ```xml
>   <settings>
>  <!--        开启驼峰命名-->
>       <setting name="mapUnderscoreToCamelCase" value="true"/>
>  <!--        开启延时加载-->
>       <setting name="lazyLoadingEnabled" value="true"/>
>   </settings>
>  
>  ```



### **关联查询,定义集合封装规则**

### Collection定义关联集合属性的封装规则

>  **多表查询, 一个部门多个员工**
>
> ```sql
> select d.id id, d.dept_name, e.id eid, e.gender gender, e.email email, e.last_name lastName
> from tbl_dept d 
> left join tbl_employee e 
> on d.id = e.d_id 
> where d.id = 1;
> ```
>
>  ```xml
>   <resultMap id="myDept" type="com.atguigu.pojo.Dept">
>         <id column="id" property="id" />
>         <result column="dept_name" property="deptName" />
>         <collection property="employeeList" ofType="com.atguigu.pojo.Employee">
>             <id column="id" property="id"/>
>             <result column="last_name" property="lastName"/>
>             <result column="email" property="email" />
>             <result column="last_name" property="lastName"/>
>             <result column="gender" property="gender" />
>         </collection>
>     </resultMap>
> 
>     <select id="getDeptAndEmp" resultMap="myDept">
>         select d.id id, d.dept_name, e.id eid, e.gender gender, e.email email, e.last_name lastName
>         from tbl_dept d
>         left join tbl_employee e
>         on d.id = e.d_id
>         where d.id = #{id};
>     </select>
>  ```
>
> 
>
>  **使用collection 进行分步查询**
>
> **DeptMapper.xml**
>
> ```xml
>  <resultMap id="myDeptStep" type="com.atguigu.pojo.Dept">
>         <id column="id" property="id" />
>         <result column="dept_name" property="deptName" />
>         <collection property="employeeList"
>                     select="com.atguigu.mapper.EmpMapper.getEmpByDeptId"
>                     column="id"></collection>
>     </resultMap>
>     <select id="getDeptByStep" resultMap="myDeptStep">
>         select * from tbl_dept where id = #{id};
>     </select>
> ```
>
> **EmpMapper.xml**
>
>  ```xml
> <select id="getEmpByDeptId" resultType="com.atguigu.pojo.Employee">
>         select * from tbl_employee where d_id = #{d_id};
> </select>
>  ```
>
> **同样含有延迟加载的效果**
>
>  
>
>  对于分步查询中要传输多个参数, 可以使用map格式
>
> 例如: 
>
> ```xml
>  <collection property="employeeList"
>                     select="com.atguigu.mapper.EmpMapper.getEmpByDeptId"
>              		以","形式分割
>                     column="{d_id=id}"></collection>
> ```
>
>  
>
> 对于全局配置的延迟加载, 若此时不需要, 可设置属性
>
> ```xml
>  <collection property="employeeList"
>                     select="com.atguigu.mapper.EmpMapper.getEmpByDeptId"
>              		以","形式分割
>                     column="{d_id=id}" fetchType="eager"></collection>
> ```



## 动态SQL

> 查询员工, 携带了哪些字段则附加该条件
>
> > where  封装查询条件
>
> > if  判断
>
> >  trim 字符串截取
> >
> > > prefix = "where"    表示前缀
> > >
> > > prefixOverrides="and"  前缀覆盖, 去掉整个字符串前面多余的字符
> > >
> > > suffix="and"  后缀
> > >
> > > suffixOverridex="" 后缀覆盖
>
> >  choose(when , otherwise) 分支查询, 只选择一个
>
> > set 设置
> >
> > if  判断
>
> >  foreach 循环, [in 查询, 批量保存, ] 
> >
> > > collection="ids" 指定集合
> > >
> > > item="item_id" 遍历的元素起名
> > >
> > > separator="," 各个元素之间的分割
> > >
> > > open="(" 开始
> > >
> > > close=")" 结束
> > >
> > > index="" 索引
> >
> > bind
> >
> > > name: 变量名
> > >
> > > value="'%' + lastName + '%'"  用于拼接值
> >
> > sql: 抽取可重用的sql片段, 方便重用
> >
> > > include 用于引入



## MyBatais缓存机制

### 一级缓存

​	**sqlSession级别的缓存**	

​	与数据库同次绘画期间查询到的数据会放在本地缓存中, 

​	以后需要相同的数据,直接缓存中获取, 不去数据库

### 	**当进行数据增删改时,会刷新所有缓存**

## 二级缓存

​	**全局缓存, 基于namespace 级别的缓存, 一个namespace对应一个二级缓存**

​	原理: 本身在一级缓存之中, 当sqlSession关闭时 , 会进行缓存转移至namespace中, 供全局使用

​	使用步骤: 

​		**1在mybatis-config.xml** 中配置

```xml
<setting name="cacheEnabled" value="true" />
```

​		**2在EmployeeMapper.xml 中配置**

 ```xml
<mapper namespace="com.atguigu.dao.EmployeeMapper">
	<cache></cache>
</mapper>
 ```

​		**3.在POJO类上增加序列化接口**

 ### 引用外部缓存

​	[尚硅谷mybatis59集, 引入EhCache](https://www.bilibili.com/video/BV1mW411M737?p=59&spm_id_from=pageDriver)

 



## Mybatis Generator 逆向工程

逆向工程, 则不需要再拼装sql等等

使用example, cirterion 可以动态sql



### Mybatis运行原理

尚硅谷mybatis视频, 71-88节

