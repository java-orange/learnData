package com.baizhi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


class SpringbootjwtApplicationTests {

    @Test
    void contextLoads() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,100);
        String token = JWT.create()
                .withClaim("userid",12)
                .withClaim("username", "xiaochen")//payload
                .withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256("!Q@W#E$R"));//签名
        System.out.println(token);
    }

    @Test
    public void test(){
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC384("!Q@W#E$R")).build();

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTY3OTI1MzMsInVzZXJpZCI6MTIsInVzZXJuYW1lIjoieGlhb2NoZW4ifQ.pfGxzorMbjrZtEEm4sbstySiWsJoMkRQfPcwOOo-fkM");

        System.out.println(verify.getClaim("userid").asInt());
        System.out.println(verify.getClaim("username").asString());
        System.out.println("过期时间: "+verify.getExpiresAt());

        //System.out.println(verify.getClaims().get("userid").asInt());
        //System.out.println(verify.getClaims().get("username").asString());



    }

}
