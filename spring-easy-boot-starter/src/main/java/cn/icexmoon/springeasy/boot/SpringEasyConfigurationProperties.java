package cn.icexmoon.springeasy.boot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SpringEasyConfigurationProperties
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午5:50
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "spring-easy")
public class SpringEasyConfigurationProperties {
    @Data
    public static class BootStarter{
        private boolean wrapResult = true;
        private boolean wrapError = true;
        private boolean timeConverter = true;
        private boolean enumConverter = true;
        private String errorPagePath = "/error";
    }
    private BootStarter bootStarter = new BootStarter();
}
