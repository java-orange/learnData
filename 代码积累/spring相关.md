

### 记录在工作中做的小实验品



#### spring下载txt文件

```java
package com.hvisions.jxh.controller;
/**
 * @author xhjing
 * @create 2021-08-19 11:58
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.ArrayList;

/**
 * <p>Title: FileController</p>
 * <p>Description: 练习文件的上传与下载</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/19</p>
 *@author : xhjing
 *@version :1.0.0
 */
@Controller
@Slf4j
public class FileController {


    @RequestMapping("export")
    public void exportTerminalKey(HttpServletRequest request, HttpServletResponse response) {

        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            strings.add("字符串string - " + i);
        }

        // 导出文件
        response.setContentType("text/plain");
        String fileName = "phone-list";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");

        StringBuffer write = new StringBuffer();
        String enter = "\r\n";

        try (ServletOutputStream outSTr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream(outSTr)) {

            // 把内容写入文件
            if (strings.size() > 0) {
                for (String string : strings) {
                    write.append(string);
                    write.append(enter);
                }
            }
            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush();
        }catch(Exception e){
            log.error("文件下载出错", e);
        }
    }



}
```



>  在进行调试时，直接在浏览器中输入对应地址，即可下载。





#### spring 的测试功能



```java
package com.hvisions.test.swagger;
/**
 * @author xhjing
 * @create 2021-08-06 15:53
 */

import com.hvisions.test.entity.HvUser;
import com.hvisions.test.repository.UserRepository;
import com.hvisions.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: TestSpec</p>
 * <p>Description: 测试jpaSpecication功能的使用</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/8/6</p>
 *@author : xhjing
 *@version :1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpec {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    @Test
    public void test(){

        Specification<HvUser> spec = new Specification<HvUser>() {
            @Override
            public Predicate toPredicate(Root<HvUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<>();
                Predicate idIN = criteriaBuilder.in(root.get("id")).value(1).value(2).value(3).value(4).value(5).value(6);
                predicateList.add(idIN);
                Predicate descLike = criteriaBuilder.like(root.get("description"), "%员%");
                predicateList.add(descLike);
                Predicate and = criteriaBuilder.and(predicateList.toArray(new Predicate[0]));

                List<Predicate> orPredicateList = new ArrayList<>();
                Predicate dateGE = criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.of(2021,8,4));
                orPredicateList.add(dateGE);
                Predicate nameLike = criteriaBuilder.like(root.get("userName"), "%j%");
                orPredicateList.add(nameLike);

                Predicate or = criteriaBuilder.or(orPredicateList.toArray(new Predicate[0]));

                return criteriaBuilder.and(and, or);

            }
        };
        List<HvUser> all = repository.findAll(spec);
        all.forEach(System.out::println);
    }
}
```



> 可直接运行， 且自动注入了之前的service 层 等等。



#### spring 整合邮件发送功能



1. **maven 依赖**

```xml
   <!-- mail 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>

```

> 注意： 若使用swagger 会产生 jar包冲突， 需排除，操作如下。

```xml

   <!--swagger-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.github.swagger2markup</groupId>
            <artifactId>swagger2markup</artifactId>
            <version>1.3.3</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.mail</groupId>
                    <artifactId>mailapi</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```



2. **yml 配置**

```yml
spring:
  # 邮箱配置
  mail:
    host: smtp.qq.com								# qq邮箱的 smtp地址
    port: 25									
    username: 823743453@qq.com						# 发信人的邮箱
    password: mulklrietisbbfgh						# 使用短信获取密码
    protocol: smtp
    default-encoding: UTF-8
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
      mail.smtp.starttls.required: true
      mail.smtp.socketFactory.port: 465
      mail.smtp.socketFactory.class: javax.net.ssl.SSLSocketFactory
      mail.smtp.socketFactory.fallback: false
```



3. **代码进行邮件发送**

```java
    @Value("${spring.mail.username}")
    private String sendEmail;					// 获取发信人。

		.....
            

        SimpleMailMessage message = new SimpleMailMessage();		// 其中自动注入了yml配置中的功能。
        message.setFrom(sendEmail);
        message.setSubject(theme);

        StringBuilder text = new StringBuilder();
        text.append("会议预定人：").append(bookName).append("\r\n")
                .append("会议时间：").append(duration).append("\r\n")
                .append("会议地点：").append(place).append("\r\n")
                .append("会议主题：").append(theme).append("\r\n")
                .append("会议主持人：").append(hostName).append("\r\n")
                .append("与会人信息：").append(participants);

        message.setText(text.toString());
        message.setTo(emailList.toArray(new String[emailList.size()]));		// 发送数组即可完成群发。
        javaMailSender.send(message);


```



具体可看代码： jxhRoom 





#### springJPA 中的 specification 条件构造器

```java
//组合条件拼接
        Specification<HvEmGateway> specification = (Specification<HvEmGateway>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            //判断name是否为空
            if (StringUtils.isNotBlank(dto.getName())) {
                list.add(criteriaBuilder.equal(root.get("name").as(String.class), dto.getName()));
            }
            //标签
            if (StringUtils.isNotBlank(dto.getLabel())) {
                list.add(criteriaBuilder.equal(root.get("label").as(String.class), dto.getLabel()));
            }
            //判断name是否为空
            if (StringUtils.isNotBlank(dto.getModel())) {
                list.add(criteriaBuilder.equal(root.get("model").as(String.class), dto.getModel()));
            }
            //地址
            if (null != dto.getAddress()) {
                list.add(criteriaBuilder.equal(root.get("address").as(String.class), dto.getAddress()));
            }
            //协议
            if (null != dto.getProtocol()) {
                list.add(criteriaBuilder.equal(root.get("protocol").as(String.class), dto.getProtocol()));
            }
            Predicate[] p = list.toArray(new Predicate[0]);
            return criteriaBuilder.and(p);
        };
```



#### SpringJpa 跳过空值覆盖

​	jpa若传递单字段，则spring以其余均为null作为对象接受，若使用save() 保存方法，则null值覆盖掉原有值，

重写监听器，进行null值跳过。

```java
package com.hvisions.log.content;

import org.hibernate.bytecode.enhance.spi.LazyPropertyInitializer;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.internal.DefaultMergeEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.property.access.internal.PropertyAccessStrategyBackRefImpl;
import org.hibernate.type.Type;

import java.util.Map;

/**
 * <p>Title: IgnoreNullEventListener</p>
 * <p>Description:
 *      自定义监听器  忽略空值
 * </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/11/10</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
public class IgnoreNullEventListener extends DefaultMergeEventListener {
 
    public static final IgnoreNullEventListener INSTANCE = new IgnoreNullEventListener();

    @Override
    protected void copyValues(EntityPersister persister, Object entity, Object target, SessionImplementor source, Map copyCache) {
        //源目标
        Object[] original = persister.getPropertyValues( entity );
        //存储目标
        Object[] targets = persister.getPropertyValues(target);

        Type[] types = persister.getPropertyTypes();
 
        Object[] copied = new Object[original.length];
        for ( int i = 0; i < types.length; i++ ) {
            if ( original[i] == null ||
                    original[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY ||
                    original[i] == PropertyAccessStrategyBackRefImpl.UNKNOWN
            ){
                copied[i] = targets[i];
            } else {
                copied[i] = types[i].replace( original[i], targets[i], source, target, copyCache );
            }
        }
 
        persister.setPropertyValues( target, copied );
    }
}
```

```java
package com.hvisions.log.configuration;

import com.hvisions.log.content.IgnoreNullEventListener;
import com.hvisions.log.entity.Setting;
import com.hvisions.log.repository.SettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * <p>Title: ProjectInitialization</p>
 * <p>Description: 项目初始化</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/11/9</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
@Component
@Slf4j
public class ProjectConfig {

    @Autowired
    private SettingRepository settingRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @PostConstruct // 构造函数之后执行
    public void init(){
        replaceListener();
        initSql();
    }

    /**
     * 使用自定义监听器， 将空值忽略
     */
    private void replaceListener() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.MERGE).clear();
        registry.getEventListenerGroup(EventType.MERGE).prependListener(IgnoreNullEventListener.INSTANCE);
    }

    /**
     * 初始化sql
     */
    private void initSql() {
        Setting setting = settingRepository.findById(1).orElse(null);
        if (setting == null) {
            log.info("Initialize the database, the default save time is always 90 days");
            setting = new Setting();
            setting.setId(1);
            setting.setEventLogSaveDuration(90L);
            setting.setConnLogSaveDuration(90L);
            setting.setSysLogSaveDuration(90L);
            settingRepository.save(setting);
        }
    }

}
```

