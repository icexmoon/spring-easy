package cn.icexmoon.springeasy.boot;

import cn.icexmoon.springeasy.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName ResultControllerAdvice
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午10:06
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class ResultControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getMethodAnnotation(ResponseBody.class) != null
                || AnnotationUtils.findAnnotation(returnType.getContainingClass(), ResponseBody.class) != null) {
            // 控制器方法或控制器类上有 @Response 注解
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof String){
            // @RequestBody 返回 String 类型，不应该被包装
            log.trace("{} 是 String 类型，不被包装", body);
            return body;
        }
        else if (body instanceof Result){
            return body;
        }
        return Result.success(body);
    }
}
