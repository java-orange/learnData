package com.hvisions.jxhRoom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>Title: DemoApplication</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/11/7</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.hvisions.jxhRoom.dao")
@EnableFeignClients(basePackages = {"com.hvisions.log.capture.client","com.hvisions.auth.client"})
@ComponentScan(basePackages = {"com.hvisions.jxhRoom","com.hvisions.log.capture.client.fallback","com.hvisions.auth.client"})
public class JxhRoomApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JxhRoomApplication.class, args);
    }

    /**
     * 可以使得项目用war包部署
     * @param builder builder
     * @return builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JxhRoomApplication.class);
    }


}
