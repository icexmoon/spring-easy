package cn.icexmoon.springeasy.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SecurityAutoConfiguration
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/2/13 11:09
 * @Version 1.0
 */
@Configuration
@MapperScan(
        basePackages = "cn.icexmoon.springeasy.security.mapper"
)
@ComponentScan(basePackages = "cn.icexmoon.springeasy.security")
public class SecurityAutoConfiguration {
}
