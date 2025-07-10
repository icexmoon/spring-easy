package cn.icexmoon.springeasy.util.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @ClassName LocalDateTimeFormatter
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午11:45
 * @Version 1.0
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public String print(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.format(formatter);
    }
}
