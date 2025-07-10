package cn.icexmoon.springeasy.util.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @ClassName IEnumModul
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午8:08
 * @Version 1.0
 */
public class IEnumModule  extends SimpleModule {
    @Override
    public void setupModule(SetupContext context) {
        context.addDeserializers(new IEnumDeserializerResolver());
    }
}
