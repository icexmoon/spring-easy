package cn.icexmoon.springeasy.util.jackson;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IEnumDeserializerResolver
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午8:08
 * @Version 1.0
 */
public class IEnumDeserializerResolver extends Deserializers.Base {
    private Map<Class<?>, IEnumJsonDeserializer> jsonDeserializerMap = Collections.synchronizedMap(new HashMap<>());
    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription desc) throws JsonMappingException {
        if (IEnum.class.isAssignableFrom(type) && type.isEnum()) {
            // 使用缓存优化性能
            IEnumJsonDeserializer iEnumJsonDeserializer = jsonDeserializerMap.get(type);
            if (iEnumJsonDeserializer == null){
                iEnumJsonDeserializer = new IEnumJsonDeserializer(type);
                jsonDeserializerMap.put(type, iEnumJsonDeserializer);
            }
            return iEnumJsonDeserializer; // 对 IEnum 实现类返回自定义解析器
        }
        return super.findEnumDeserializer(type, config, desc); // 其他枚举仍用默认逻辑
    }
}
