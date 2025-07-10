package cn.icexmoon.springeasy.test.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName MyFilter
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/10 上午10:35
 * @Version 1.0
 */
@Slf4j
@WebFilter("/error/servlet")
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpServletRequest) ||
                !(response instanceof HttpServletResponse)) {
            throw new RuntimeException("这不是一个 HTTP 请求");
        }
        String url = httpServletRequest.getRequestURI();
        log.debug("MyFilter is called.");
        // 模拟过滤器存在异常的情况
        if (url.equals("/error/servlet")){
            int i = 1/0;
        }
        chain.doFilter(request, response);
    }
}