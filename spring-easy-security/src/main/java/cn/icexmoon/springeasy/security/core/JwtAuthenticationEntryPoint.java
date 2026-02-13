package cn.icexmoon.springeasy.security.core;

import cn.icexmoon.springeasy.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName JwtAuthenticationEntryPoint
 * @Description 验证失败的响应处理
 * @Author icexmoon@qq.com
 * @Date 2026/1/6 15:32
 * @Version 1.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 设置HTTP响应状态码为 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应内容类型为JSON，并指定字符编码
        response.setContentType("application/json;charset=utf-8");

        // 构建一个清晰的JSON格式错误信息返回给客户端
        Result<String> errorResp = Result.fail("error.unauthorized", "认证失败：未提供有效的JWT令牌或令牌已过期。", request.getRequestURI());

        // 将错误信息写入响应流
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResp));
        response.getWriter().flush();
    }
}