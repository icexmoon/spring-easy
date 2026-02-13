package cn.icexmoon.springeasy.security.service;

import cn.icexmoon.springeasy.security.entity.UserAuthority;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName UserAuthorityService
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/19 09:29
 * @Version 1.0
 */
public interface UserAuthorityService extends IService<UserAuthority> {
    /**
     * 根据用户名获取用户权限
     * @param username 用户名
     * @return 用户权限
     */
    List<UserAuthority> listByUsername(String username);

    /**
     * 根据用户名获取用户角色
     * @param username 用户名
     * @return 用户角色
     */
    List<String> getRolesByUsername(String username);
}
