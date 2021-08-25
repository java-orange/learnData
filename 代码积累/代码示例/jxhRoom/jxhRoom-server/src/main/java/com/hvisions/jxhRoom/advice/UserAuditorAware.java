package com.hvisions.jxhRoom.advice;

import com.hvisions.common.consts.CookieConst;
import com.hvisions.common.consts.RedisConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * <p>Title: UserAuditorAware</p>
 * <p>Description: 通过使用jpa的audit功能。需要配置h-visions.audit.mode=jpa</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2020/4/26</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@Configuration
@Slf4j
@EnableJpaAuditing
public class UserAuditorAware implements AuditorAware<Integer> {
    @Autowired
    HttpServletRequest request;
    /**
     * redis处理对象
     */
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取当前用户信息
     * 如果token不是空，根据sessionId获取redis中的用户id,如果没有对应的header信息。或者其中出现了异常。用户id会被设置为0
     *
     * @return 当前用户信息
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
//        String token = request.getHeader(CookieConst.AUTH_TOKEN);
//        if (token == null) {
//            return Optional.of(0);
//        } else {
//            try {
//                String userIdStr = stringRedisTemplate.opsForValue()
//                        .get(String.format(RedisConst.AUTH_REDIS_PREFIX, token));
//                if (userIdStr == null) {
//                    log.warn("获取audit用户信息错误，token对应的用户id为空," + token);
//                    return Optional.of(0);
//                }
//                int userId = Integer.valueOf(userIdStr);
//                return Optional.of(userId);
//            } catch (Exception ex) {
//                log.warn("audit用户id格式转换异常，请检查redis中存储的用户id格式是否异常," + token);
//                return Optional.of(0);
//            }
//        }
        return Optional.of(1);
    }

}









