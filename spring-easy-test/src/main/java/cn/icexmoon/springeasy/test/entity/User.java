package cn.icexmoon.springeasy.test.entity;

import cn.icexmoon.springeasy.util.IDescEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User {
    public enum Sex implements IDescEnum<Integer> {
        MALE(5, "男"), FEMALE(6, "女");
        private final Integer value;
        private final String desc;

        Sex(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String getDesc() {
            return desc;
        }
    }

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 0 男，1 女
     */
    private Sex sex;
}