package cn.icexmoon.springeasy.util.converter;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * @ClassName Str2EnumConverter
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午3:10
 * @Version 1.0
 */
public class Str2EnumConverter<T extends Enum<?> & IEnum<Integer>> implements Converter<String, T> {
    Class<T> cls;

    Str2EnumConverter(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T convert(String source) {
        T[] enumConstants = cls.getEnumConstants();
        Integer sourceVal = Integer.valueOf(source);
        for (T enumInstance : enumConstants) {
            if (sourceVal.equals(enumInstance.getValue())){
                return enumInstance;
            }
        }
        return null;
    }
}
