package cn.icexmoon.springeasy.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName DruidConfig
 * @Description 数据源
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 16:23
 * @Version 1.0
 */
@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnMissingBean
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
