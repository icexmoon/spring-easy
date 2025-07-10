package cn.icexmoon.springeasy.boot;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @ClassName IEnumControllerAdvice
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午3:00
 * @Version 1.0
 */
@ControllerAdvice
public class IEnumControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
//        webDataBinder.addCustomFormatter();
    }
}
