package cn.icexmoon.springeasy.util.jackson;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;

/**
 * @ClassName IEnumJsonDeserializer
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午5:38
 * @Version 1.0
 */
@Slf4j
public class IEnumJsonDeserializer extends JsonDeserializer<Enum<?>> {
    private final Class<?> cls;
    private Enum<?>[] enumConstants = new Enum[0];

    public IEnumJsonDeserializer(Class<?> cls) {
        this.cls = cls;
        if (cls.isEnum()){
            Class<Enum<?>> enumCls = (Class<Enum<?>>) cls;
            // 将枚举常量保存在缓存中，优化查询性能
            enumConstants = enumCls.getEnumConstants();
        }
        else{
            log.error("类型：{}不是枚举类型，IEnumJsonDeserializer 解析器只能作用于每句类型", cls.getName());
        }
    }

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (cls.isEnum()){
            int intValue = p.getIntValue();
            for (Enum<?> enumConstant : enumConstants) {
                if(enumConstant instanceof IEnum<?>){
                    Serializable value = ((IEnum<?>) enumConstant).getValue();
                    if (value.equals(intValue)){
                        return enumConstant;
                    }
                }
            }
            log.error("待转换的目标类型：{}，没有找到匹配的枚举常量", cls.getName());
        }
        return null;
    }
}
