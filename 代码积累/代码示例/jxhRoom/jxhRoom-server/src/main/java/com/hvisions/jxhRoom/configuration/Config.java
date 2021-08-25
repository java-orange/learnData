package com.hvisions.jxhRoom.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.hvisions.common.advice.ControllerExceptionHandler;
import com.hvisions.common.advice.HvisionsApiResultHandler;
import com.hvisions.common.advice.ResultFactory;
import com.hvisions.common.component.HvDictionarySerializer;
import com.hvisions.common.component.HvStringDeserialzer;
import com.hvisions.common.component.HvisionsI18nInternational;
import com.hvisions.common.interfaces.DictionaryObject;
import com.hvisions.common.utils.HvisionsExceptionMapper;
import com.hvisions.log.capture.aop.LogAopUtil;
import com.hvisions.log.capture.client.LogCaptureClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>Title: AuthConfig</p>
 * <p>Description: 统一配置，可以重写其中的类来实现特定的策略</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2018/11/3</p>
 *
 * @author :leiming
 * @version :1.0.0
 */
@Configuration
public class Config {
    @Value("${spring.messages.basename}")
    private String basename;

    @Value("${spring.messages.cache-seconds}")
    private long cacheMillis;

    @Value("${spring.messages.encoding}")
    private String encoding;
    /**
     * Date格式化字符串
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * DateTime格式化字符串
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Time格式化字符串
     */
    private static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * 日志客户端
     */
    @Autowired
    LogCaptureClient logCaptureClient;
    /**
     * redis处理对象
     */
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * @return 国际化类
     */
    @Bean
    HvisionsI18nInternational getHVisionsI18nInternational() {
        return new HvisionsI18nInternational(basename, cacheMillis, encoding);
    }

    /**
     * 会自动将所有com.hvisions包下的所有控制器进行拦截并且记录
     * 请求时间，请求人，请求url，body传参，返回值，执行是否成功
     * 调用的哪个服务。此功能可以通过yml中hvisions.log.enable 打开或者关闭
     *
     * @return AOP日志记录
     */
    @Bean
    @ConditionalOnProperty(prefix = "h-visions.log", name = "enable", havingValue = "true")
    LogAopUtil getLogAop() {
        return new LogAopUtil(stringRedisTemplate, logCaptureClient);
    }


    /**
     * @return 异常信息处理类
     */
    @Bean
    HvisionsExceptionMapper getHVisionsExceptionMapper() {
        return new HvisionsExceptionMapper();
    }

    /**
     * @param hVisionsI18nInternational 国际化bean
     * @param hVisionsExceptionMapper   异常映射类
     * @return 异常转换类
     */
    @Bean
    ResultFactory getResultVOFactory(HvisionsI18nInternational hVisionsI18nInternational,
                                     HvisionsExceptionMapper hVisionsExceptionMapper) {
        return new ResultFactory(hVisionsI18nInternational, hVisionsExceptionMapper);
    }

    /**
     * 只要控制器之后的异常都可以进行捕获，会自动根据message查找国际化信息并且返回，如果没有定义异常代码，异常代码会是10000，
     * 如果没有定义国际化消息，会默认返回message，支持国际化模板调用
     *
     * @param resultVOFactory 异常转换类
     * @return 全局统一异常处理bean
     */
    @Bean
    ControllerExceptionHandler getControllerExceptionHandler(ResultFactory resultVOFactory) {
        return new ControllerExceptionHandler(resultVOFactory);
    }

    /**
     * 作用：自动包装成ResultVO，如果想要自己处理。可以在方法上添加注解@ApiResultIgnore
     *
     * @return 全局统一结果处理类
     */
    @Bean
    HvisionsApiResultHandler getHVisionsApiResultHandler() {
        return new HvisionsApiResultHandler();
    }


    /**
     * 增加部分框架自定义的序列化和反序列化类
     *
     * @return jackson配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer initJackson(StringRedisTemplate stringRedisTemplate) {
        return builder -> {
            //支持数据字典键值根据value自动填充,DTO继承DictionaryObject,键字段上增加注解@DictionaryKey即可实现
            builder.serializerByType(DictionaryObject.class, new HvDictionarySerializer(stringRedisTemplate));
            //自动对入参对象中的String进行trim操作。不需要此项功能的可以注释掉
            builder.deserializerByType(String.class, new HvStringDeserialzer());
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
        };
    }

}
