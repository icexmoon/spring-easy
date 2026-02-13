package cn.icexmoon.springeasy.security.util;

import cn.icexmoon.springeasy.util.properties.SpringEasyConfigurationProperties;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RefreshTokenRedis
 * @Description 刷新令牌存储在Redis中
 * @Author icexmoon@qq.com
 * @Date 2026/1/7 13:08
 * @Version 1.0
 */
@Component
public class RefreshTokenRedis {
    public static final String REFRESH_TOKEN_KEY_PREFIX = "refresh-token:";
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SpringEasyConfigurationProperties springEasyConfigurationProperties;

    /**
     * 保存刷新令牌
     * @param username 用户名
     * @param refreshToken 刷新令牌
     */
    public void save(@NonNull String username, @NonNull String refreshToken) {
        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_KEY_PREFIX + username,
                refreshToken,
                springEasyConfigurationProperties.getJwt().getRefreshTokenExpirationHours(),
                TimeUnit.HOURS
        );
    }

    /**
     * 获取刷新令牌
     * @param username 用户名
     * @return 刷新令牌
     */
    public String get(@NonNull String username) {
        return redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY_PREFIX + username);
    }

    /**
     * 验证刷新令牌
     * @param username 用户名
     * @param refreshToken 刷新令牌
     * @return 验证结果
     */
    public boolean validate(@NonNull String username, @NonNull String refreshToken){
        return refreshToken.equals(get(username));
    }

    /**
     * 删除刷新令牌
     * @param username 用户名
     */
    public void delete(String username) {
        redisTemplate.delete(REFRESH_TOKEN_KEY_PREFIX + username);
    }
}
