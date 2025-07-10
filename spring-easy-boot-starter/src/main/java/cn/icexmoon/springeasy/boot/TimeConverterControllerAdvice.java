package cn.icexmoon.springeasy.boot;

import cn.icexmoon.springeasy.util.converter.LocalDateTimeFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @ClassName TimeConverterControllerAdvice
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午11:29
 * @Version 1.0
 */
@ControllerAdvice
public class TimeConverterControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new LocalDateTimeFormatter());
    }
}
