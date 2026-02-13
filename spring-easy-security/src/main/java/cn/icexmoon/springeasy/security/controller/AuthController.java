package cn.icexmoon.springeasy.security.controller;

import cn.icexmoon.springeasy.security.service.JwtTokenService;
import cn.icexmoon.springeasy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AuthController
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public record LoginRequest(String username, String password) {
    }
    public record LoginResponse(String accessToken, String refreshToken) {
    }
    public record RefreshRequest(String refreshToken) {
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    /**
     * 登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 进行认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 登录成功后返回 access token 和 refresh token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails == null) {
            throw new UsernameNotFoundException("用户[" + loginRequest.username() + "]不存在");
        }
        return Result.success(jwtTokenService.generateToken(userDetails));
    }

    /**
     * 刷新令牌
     *
     * @param refreshRequest 刷新令牌请求
     * @return 刷新令牌结果
     */
    @PostMapping("/refresh")
    public Result<LoginResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        // 验证 refreshToken 并生成新的访问令牌和刷新令牌
        LoginResponse loginResponse = jwtTokenService.validateToken(refreshRequest.refreshToken());
        return Result.success(loginResponse);
    }

}