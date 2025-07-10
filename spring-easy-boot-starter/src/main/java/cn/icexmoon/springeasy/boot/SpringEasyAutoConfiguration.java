package cn.icexmoon.springeasy.boot;

import cn.icexmoon.springeasy.util.converter.Str2EnumConverterFactory;
import cn.icexmoon.springeasy.util.jackson.IEnumJsonSerializer;
import cn.icexmoon.springeasy.util.jackson.IEnumModule;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

/**
 * @ClassName SpringEasyAutoConfiguration
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午9:38
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(SpringEasyConfigurationProperties.class)
public class SpringEasyAutoConfiguration {
    /**
     * 全局返回值封装
     * 将控制器方法返回值封装成标准返回值
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBooleanProperty(name = "spring-easy.boot-starter.wrap-result", havingValue = true, matchIfMissing = true)
    public ResultControllerAdvice resultControllerAdvice() {
        return new ResultControllerAdvice();
    }

    /**
     * 异常封装相关的自动配置类
     */
    @Configuration
    @ConditionalOnBooleanProperty(name = "spring-easy.boot-starter.wrap-error", havingValue = true, matchIfMissing = true)
    public static class ErrorWrapAutoConfiguration {
        /**
         * 异常拦截器
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public ErrorControllerAdvice errorControllerAdvice() {
            return new ErrorControllerAdvice();
        }


        /**
         * 处理 Servlet 异常的控制器
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public ServletErrorController servletErrorController(){
            return new ServletErrorController();
        }

        /**
         * 注册错误处理页面到 Servlet
         * @param properties
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public ErrorPageRegistrar errorPageRegistrar(SpringEasyConfigurationProperties properties) {
            return new ErrorPageRegistrar() {
                @Override
                public void registerErrorPages(ErrorPageRegistry registry) {
                    registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, properties.getBootStarter().getErrorPagePath()));
                }
            };
        }
    }

    /**
     * 时间类型转换相关的自动配置
     */
    @Configuration
    @ConditionalOnBooleanProperty(name = "spring-easy.boot-starter.time-converter", havingValue = true, matchIfMissing = true)
    public static class TimeConverterAutoConfiguration {
        /**
         * LocalDateTime 的类型转换器，使用 yyyy-MM-dd HH:mm:ss 格式
         * 用于控制器方法参数类型为 LocalDateTime 时进行转换
         *
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public TimeConverterControllerAdvice timeConverterControllerAdvice() {
            return new TimeConverterControllerAdvice();
        }

    }

    /**
     * 扩展 jackson 的类型转换
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(SpringEasyConfigurationProperties springEasyConfigurationProperties) {
        return builder -> {
            if (springEasyConfigurationProperties.getBootStarter().isTimeConverter()) {
                // 定义日期时间格式
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 注册序列化与反序列化器
                builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
                builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
            }
            if (springEasyConfigurationProperties.getBootStarter().isEnumConverter()) {
                // 添加 IEnum 类型到 String 的编码器，用于 JSON
                builder.serializerByType(IEnum.class, new IEnumJsonSerializer());
            }
        };
    }

    @Configuration
    @ConditionalOnBooleanProperty(name = "spring-easy.boot-starter.enum-converter", havingValue = true, matchIfMissing = true)
    public static class IEnumConverterAutoConfiguration implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            // 添加 String 到 IEnum 类型的转换器，用于控制器参数解析
            registry.addConverterFactory(new Str2EnumConverterFactory<>());
        }

        /**
         * 添加 String 到 IEnum 的解析器，用于 @RequestBody JSON 消息解析
         *
         * @return
         */
        @Bean
        @ConditionalOnMissingBean
        public IEnumModule iEnumModule() {
            return new IEnumModule();
        }
    }
}
