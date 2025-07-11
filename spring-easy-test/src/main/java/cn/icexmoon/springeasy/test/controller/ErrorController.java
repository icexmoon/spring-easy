package cn.icexmoon.springeasy.test.controller;

import cn.icexmoon.springeasy.util.BusinessException;
import cn.icexmoon.springeasy.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName ErrorController
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/10 上午10:20
 * @Version 1.0
 */
@RestController
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/default")
    @SuppressWarnings("all")
    public Result<Void> defaultError() {
        int i = 1 / 0;
        return Result.success();
    }

    @GetMapping("/business")
    @SuppressWarnings("all")
    public Result<Void> businessError() {
        try {
            int i = 1 / 0;
        } catch (Throwable e) {
            Map<String, ?> exceptionData = Map.of(
                    "cause", e,
                    "businessCode", "1001");
            throw BusinessException.builder()
                    .cause(e)
                    .message(e.getMessage())
                    .data(exceptionData)
                    .build();
        }
        return Result.success();
    }

    @GetMapping("/servlet")
    public Result<Void> servletError() {
        return Result.success();
    }
}
