package cn.icexmoon.springeasy.security.core;

import cn.icexmoon.springeasy.security.util.JwtTokenProvider;
import cn.icexmoon.springeasy.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @ClassName JwtAuthenticationFilter
 * @Description JWT认证过滤器
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 16:28
 * @Version 1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                String username;
                try {
                    username = tokenProvider.getUsernameFromJWT(jwt);
                } catch (ExpiredJwtException e) {
                    // Token过期
                    sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "AUTH_EXPIRED", "登录信息已过期，请使用刷新令牌更新访问令牌");
                    return; // 注意：立即返回，不再执行后续过滤器
                } catch (SignatureException e) {
                    // 🔴 捕获签名异常
                    sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "INVALID_SIGNATURE", "令牌无效，拒绝访问");
                    return;
                } catch (MalformedJwtException e) {
                    // Token格式错误
                    sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "MALFORMED_TOKEN", "令牌格式错误");
                    return;
                } catch (Exception e) {
                    // 其他异常
                    sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "SYSTEM_ERROR", "系统异常，请稍后重试");
                    return;
                }

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
            throw ex;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从请求中获取JWT
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 统一发送错误响应的方法
     */
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String errorCode, String errorMessage) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 构建一个结构化的错误响应体
        Result<Void> errorDetails = Result.fail(errorCode, errorMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
