package cn.icexmoon.springeasy.util.properties;

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
        private boolean springSecurity = true;
        private String errorPagePath = "/error";
    }
    @Data
    public static class Jwt{
        // access token 的有效时长，默认 半小时
        private long expirationMs = 1800000;
        // refresh token 的有效时长，默认 24 小时
        private long refreshTokenExpirationHours = 24;
    }
    private BootStarter bootStarter = new BootStarter();
    private Jwt jwt = new Jwt();
}
