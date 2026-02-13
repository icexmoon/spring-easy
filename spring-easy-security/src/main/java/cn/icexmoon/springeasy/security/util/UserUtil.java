package cn.icexmoon.springeasy.security.util;

import cn.icexmoon.springeasy.security.entity.CustomUser;
import cn.icexmoon.springeasy.util.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName UserUtil
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/16 14:59
 * @Version 1.0
 */
public class UserUtil {
    public static CustomUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw BusinessException.builder()
                    .code("NOT_LOGIN")
                    .message("用户未登录")
                    .httpStatusCode(401)
                    .build();
        }
        return (CustomUser) authentication.getPrincipal();
    }
}
