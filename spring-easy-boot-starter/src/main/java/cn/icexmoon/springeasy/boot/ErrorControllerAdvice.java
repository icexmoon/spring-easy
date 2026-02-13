package cn.icexmoon.springeasy.boot;

import cn.icexmoon.springeasy.util.BusinessException;
import cn.icexmoon.springeasy.util.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ErrorControllerAdvice
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午10:16
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class ErrorControllerAdvice {
    /**
     * 全局异常处理
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result<Void> exceptionHandler(Throwable e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return Result.fail(e.getMessage());
    }

    /**
     * 业务异常处理
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result<Object> businessExceptionHandler(BusinessException ex, HttpServletResponse response) {
        log.error("handleBusinessException: {}", ex.getMessage(), ex);
        // 如果异常指定了HTTP状态码，重写响应状态码
        if (ex.getHttpStatusCode() != null){
            response.setStatus(ex.getHttpStatusCode());
        }
        return Result.fail(ex.getData(), ex.getMessage(), ex.getCode());
    }
}
