package cn.icexmoon.springeasy.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName Authority
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/16 16:41
 * @Version 1.0
 */
@TableName("se_user_authority")
@Data
public class UserAuthority implements GrantedAuthority {
    public static final String ROLE_PREFIX = "ROLE_";
    private String username;
    private String authority;
}
