package cn.icexmoon.springeasy.test.controller;

import cn.icexmoon.springeasy.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName HelloController
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午10:29
 * @Version 1.0
 */
@RequestMapping("/hello")
@RestController
@Slf4j
public class HelloController {
    @GetMapping("/say")
    public Map<String, String> say() {
        return Map.of("msg", "hello");
    }

    @GetMapping("/date")
    public LocalDate date(@RequestParam LocalDate date) {
        log.info(date.toString());
        return date;
    }

    @GetMapping("/time")
    public LocalDateTime time(@RequestParam LocalDateTime time) {
        log.info(time.toString());
        return time;
    }

    @GetMapping("/msg")
    public String msg() {
        return "<h1>hello</h1>";
    }

    @GetMapping("/msg2")
    public Result<String> msg2() {
        return Result.success("hello", null);
    }

    @GetMapping("/void")
    public void voidMethod() {
    }

    @GetMapping("/boolean")
    public boolean booleanMethod() {
        return false;
    }

    @GetMapping("/int")
    public int intMethod() {
        return 11;
    }
}
