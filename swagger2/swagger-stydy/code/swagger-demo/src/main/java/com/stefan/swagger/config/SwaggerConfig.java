package com.stefan.swagger.config;

import com.stefan.swagger.controller.HelloController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2// 开启Swagger2
public class SwaggerConfig {

    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("hhhhhh");
    }

    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("ttttt");
    }

    @Bean
    public Docket docket4(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("jjjjj");
    }


    //配置Swagger的Docket实例
    // enable是否启动Swagger，如果为false，则Swagger不能在浏览器中访问
    @Bean
    public Docket docket(Environment environment){
        // 设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev","test");
        // 获取项目的环境：通过environment.acceptsProfiles判断是否处于自己设定的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        System.out.println(flag);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("wangyunjie")
                .enable(flag)
                .select()
                // RequestHandlerSelectors配置要扫描接口的方式
                // basePackage指定要扫描的包
                // any()：扫描全部
                // none()：不扫描
                // withClassAnnotation：扫描类上的注解
                // withMethodAnnotation：扫描方法上的注解
                // withClassAnnotation
                .apis(RequestHandlerSelectors.basePackage("com.stefan.swagger"))
                // paths() 过滤什么路径
//                .paths(PathSelectors.ant("/stefan/**"))
                .build();
    }

    // 配置swagger信息 = apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("Klaus", "http://127.0.0.1:8080/files/test.png", "1424245538@qq.com");

        return new ApiInfo(
                "Stefan的SwaggerAPI文档",
                "人生若只如初见",
                "v1.0",
                "http://127.0.0.1:8080/files/test.png",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
