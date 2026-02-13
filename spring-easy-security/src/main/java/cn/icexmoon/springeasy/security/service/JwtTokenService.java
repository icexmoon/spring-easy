package cn.icexmoon.springeasy.security.service;

import cn.icexmoon.springeasy.security.controller.AuthController;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @ClassName JwtTokenService
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/7 12:39
 * @Version 1.0
 */
public interface JwtTokenService {
    /**
     * 生成token
     *
     * @param userDetails 用户信息
     * @return token
     */
    AuthController.LoginResponse generateToken(UserDetails userDetails);

    /**
     * 验证 refresh token
     *
     * @param refreshToken refresh token
     * @return 验证结果
     */
    AuthController.LoginResponse validateToken(String refreshToken);
}
