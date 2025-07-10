package cn.icexmoon.springeasy.util.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @ClassName LocalDateFormatter
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午11:33
 * @Version 1.0
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

    // 格式模板
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, formatter);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(formatter);
    }
}
