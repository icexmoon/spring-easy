package cn.icexmoon.springeasy.util.jackson;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @ClassName EnumSerializer
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午3:23
 * @Version 1.0
 */
public class IEnumJsonSerializer extends JsonSerializer<IEnum<Integer>> {
    @Override
    public void serialize(IEnum<Integer> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }
}
