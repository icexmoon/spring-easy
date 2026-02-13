package cn.icexmoon.springeasy.security.util;

import cn.icexmoon.springeasy.security.entity.RefreshToken;
import cn.icexmoon.springeasy.util.properties.SpringEasyConfigurationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName JwtTokenProvider
 * @Description JWT令牌提供者
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 16:26
 * @Version 1.0
 */
@Component
public class JwtTokenProvider {
    @Autowired
    private SpringEasyConfigurationProperties springEasyConfigurationProperties;
    // 随机生成一个长度足够的密钥
    private final SecretKey key = Jwts.SIG.HS512.key().build();

    /**
     * 生成 JWT 令牌（access token）
     * @param userDetails Spring Security 用户信息
     * @return JWT 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + springEasyConfigurationProperties.getJwt().getExpirationMs());

        return Jwts.builder()
                .subject(userDetails.getUsername()) // 通常存放用户名
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key) // 指定算法和密钥
                .compact();
    }

    /**
     * 生成 refresh token
     * @param userDetails Spring Security 用户信息
     * @return refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        RefreshToken refreshToken = createRefreshTokenInstance(userDetails);
        return generateRefreshToken(refreshToken);
    }

    /**
     * 创建 refresh token 实例
     * @param userDetails Spring Security 用户信息
     * @return refresh token 实例
     */
    private RefreshToken createRefreshTokenInstance(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + springEasyConfigurationProperties.getJwt().getRefreshTokenExpirationHours() * 60 * 60 * 1000);
        return RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .userName(userDetails.getUsername())
                .issuedAt( now)
                .expiryDate(expiryDate)
                .build();
    }

    /**
     * 生成 refresh token
     * @param refreshToken refresh token 实例
     * @return refresh token
     */
    private String generateRefreshToken(RefreshToken refreshToken){
        return Jwts.builder()
                .id(refreshToken.getId())
                .subject(refreshToken.getUserName()) // 通常存放用户名
                .issuedAt(refreshToken.getIssuedAt())
                .expiration(refreshToken.getExpiryDate())
                .signWith(key) // 签名
                .compact();
    }

    /**
     * 从 Token 中获取用户名，如果验证失败则抛出异常
     * @param token JWT令牌
     * @return 用户名
     * @throws JwtException 验证失败异常
     */
    public String getUsernameFromJWT(String token) throws JwtException{
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claimsJws.getPayload().getSubject();
    }
}
