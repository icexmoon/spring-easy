package cn.icexmoon.springeasy.util;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * @ClassName IDescEnum
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午2:17
 * @Version 1.0
 */
public interface IDescEnum<T extends Serializable> extends IEnum<T> {
    String getDesc();
}
