package cn.icexmoon.springeasy.security.service.impl;

import cn.icexmoon.springeasy.security.entity.CustomUser;
import cn.icexmoon.springeasy.security.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserDetailServiceImpl
 * @Description 重写 UserDetailsService，获取用户信息
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 17:50
 * @Version 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private CustomUserService userService;
    /**
     * 从数据库获取用户信息
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userService.getByUsername( username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在" + username);
        }
        return user;
    }
}