package cn.icexmoon.springeasy.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName RoleAuthority
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/19 08:33
 * @Version 1.0
 */
@TableName("se_role_authority")
@Data
public class RoleAuthority {
    private String role;
    private String authority;
}
