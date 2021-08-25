package com.hvisions.jxhRoom.configuration;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SwaggerConfig</p>
 * <p>Description: swagger 功能开启的配置类,必须要配置h-visions.swagger.enable 为true</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/9/27</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "h-visions.swagger", name = "enable", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //添加全局参数 请求token
        ticketPar.name("token").description("请求token")
                .modelRef(new ModelRef("string")).parameterType("header")
                //header中的ticket参数非必填，传空也可以
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                //添加全局参数。token
                .globalOperationParameters(pars)
                //修改界面中的一些元素
                .apiInfo(new ApiInfoBuilder()
                        //后面的信息可以根据项目自己的需求进行修改
                        .title("慧程 api文档")
                        .description("慧程 后端接口文档")
                        .version("1.0.0")
                        .build());

    }

}