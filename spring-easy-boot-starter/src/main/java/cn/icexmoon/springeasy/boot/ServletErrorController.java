package cn.icexmoon.springeasy.boot;

import cn.icexmoon.springeasy.util.Result;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ServletErrorController
 * @Description 用于处理 Servlet 异常捕获后响应的控制器
 * @Author icexmoon@qq.com
 * @Date 2025/7/10 上午11:01
 * @Version 1.0
 */
@Controller
@Slf4j
public class ServletErrorController {

    @RequestMapping("#{@'spring-easy-cn.icexmoon.springeasy.util.properties.SpringEasyConfigurationProperties'.bootStarter.errorPagePath}")
    @ResponseBody
    public Result<?> error(HttpServletRequest request) {
        Throwable error = (Throwable) request.getAttribute((RequestDispatcher.ERROR_EXCEPTION));
        log.error(error.getMessage(), error);
        return Result.fail("Servlet 错误：" + error.getMessage());
    }
}
