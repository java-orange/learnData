package com.atguigu.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*====================================================
                时间: 2022-05-18
                讲师: 刘  辉
                出品: 尚硅谷教学团队
======================================================*/
@SpringBootConfiguration
@EnableSwagger2 //开启Swagger支持
public class SwaggerConfig {

    @Bean
    public Docket getAdminDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(getAdminApiInfo())
                .select()
                .build();
    }
    public ApiInfo getAdminApiInfo(){
        return  new ApiInfoBuilder()
                .title("springCloud微服务实战")
                .description("尚硅谷值之Gradle构建微服务实战")
                .version("1.0")
                //.contact(new Contact("LH","http://www.atguigu.com","xxx@163.com"))
                .build();
    }


}
