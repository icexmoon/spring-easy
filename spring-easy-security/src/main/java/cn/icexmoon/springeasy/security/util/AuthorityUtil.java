package cn.icexmoon.springeasy.security.util;

import cn.hutool.core.util.StrUtil;
import cn.icexmoon.springeasy.security.entity.UserAuthority;

/**
 * @ClassName AuthorityUtil
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/19 09:35
 * @Version 1.0
 */
public class AuthorityUtil {
    /**
     * 判断权限是否为角色权限
     * @param authority 权限
     * @return 是否为角色权限
     */
    public static boolean isRoleAuthority(String authority) {
        if (StrUtil.isEmpty(authority)) {
            return false;
        }
        return authority.startsWith(UserAuthority.ROLE_PREFIX);
    }

    /**
     * 获取角色名称
     * @param authority 权限
     * @return 角色名称
     */
    public static String getRoleName(String authority) {
        if (!isRoleAuthority(authority)) {
            return null;
        }
        return authority.substring(UserAuthority.ROLE_PREFIX.length());
    }
}
