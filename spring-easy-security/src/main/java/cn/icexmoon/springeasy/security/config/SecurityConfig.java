package cn.icexmoon.springeasy.security.config;

import cn.icexmoon.springeasy.security.core.CustomAccessDeniedHandler;
import cn.icexmoon.springeasy.security.core.JwtAuthenticationEntryPoint;
import cn.icexmoon.springeasy.security.core.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName SecurityConfig
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 16:18
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 配置路径认证规则
     * @param http HttpSecurity
     * @param customAccessDeniedHandler 自定义403处理
     * @param jwtAuthenticationEntryPoint 自定义401处理
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomAccessDeniedHandler customAccessDeniedHandler,
                                           JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())// 通常JWT无状态应用可禁用CSRF
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态会话
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**","/error").permitAll() // 登录注册公开
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 管理员可访问
                        .anyRequest().authenticated() // 其他请求需认证
                )
                .exceptionHandling((exception) ->
                        exception
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 添加JWT过滤器
        log.debug("SecurityFilterChain 已加载");
        return http.build();
    }

    /**
     * 认证管理器
     * @param userDetailsService 用户详情服务
     * @param passwordEncoder 密码编码器
     * @return 认证管理器
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    /**
     * 密码编码器
     * @return 密码编码器
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
