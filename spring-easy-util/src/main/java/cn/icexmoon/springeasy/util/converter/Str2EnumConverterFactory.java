package cn.icexmoon.springeasy.util.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @ClassName Str2EnumConverterFactory
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午3:12
 * @Version 1.0
 */
public class Str2EnumConverterFactory<T extends Enum<?> & IEnum<Integer>> implements ConverterFactory<String, T> {

    @Override
    public <T1 extends T> Converter<String, T1> getConverter(Class<T1> targetType) {
        return new Str2EnumConverter<>(targetType);
    }
}
