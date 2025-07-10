package cn.icexmoon.springeasy.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName SpringEasyTestApplication
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午10:27
 * @Version 1.0
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan({"cn.icexmoon.springeasy.test.mapper"})
public class SpringEasyTestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringEasyTestApplication.class, args);
    }
}
