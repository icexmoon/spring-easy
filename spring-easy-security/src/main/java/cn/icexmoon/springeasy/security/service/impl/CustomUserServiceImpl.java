package cn.icexmoon.springeasy.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.icexmoon.springeasy.security.entity.CustomUser;
import cn.icexmoon.springeasy.security.entity.RoleAuthority;
import cn.icexmoon.springeasy.security.entity.UserAuthority;
import cn.icexmoon.springeasy.security.mapper.CustomUserMapper;
import cn.icexmoon.springeasy.security.service.CustomUserService;
import cn.icexmoon.springeasy.security.service.RoleAuthorityService;
import cn.icexmoon.springeasy.security.service.UserAuthorityService;
import cn.icexmoon.springeasy.security.util.AuthorityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CustomUserServiceImpl
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/16 18:06
 * @Version 1.0
 */
@Service
@Slf4j
public class CustomUserServiceImpl extends ServiceImpl<CustomUserMapper, CustomUser> implements CustomUserService {
    @Autowired
    private UserAuthorityService userAuthorityService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;

    private CustomUser getOneByUserName(@NonNull String username) {
        return getBaseMapper().selectOne(new QueryWrapper<CustomUser>().eq("username", username));
    }

    @Override
    public CustomUser getByUsername(@NonNull String username) {
        CustomUser customUser = getOneByUserName(username);
        if (customUser == null) {
            throw new UsernameNotFoundException("用户不存在" + username);
        }
        // 获取用户相关的权限
        List<UserAuthority> userAuthorities = userAuthorityService.listByUsername(username);
        if (!CollectionUtil.isEmpty(userAuthorities)) {
            for (UserAuthority userAuthority : userAuthorities) {
                String authority = userAuthority.getAuthority();
                if (StrUtil.isEmpty(authority)) {
                    continue;
                }
                // 如果是普通权限，直接添加
                if (!AuthorityUtil.isRoleAuthority(authority)) {
                    log.debug("添加普通权限：{}", authority);
                    customUser.addNormalAuthority(authority);
                } else {
                    // 角色权限，添加角色权限
                    String roleName = AuthorityUtil.getRoleName(authority);
                    customUser.addRoleAuthority(roleName);
                    log.debug("添加角色权限：{}", roleName);
                    // 获取角色相关的权限
                    List<RoleAuthority> roleAuthorities = roleAuthorityService.listByRole(roleName);
                    if (!CollectionUtil.isEmpty(roleAuthorities)) {
                        for (RoleAuthority roleAuthority : roleAuthorities) {
                            String authorityText = roleAuthority.getAuthority();
                            if (StrUtil.isEmpty(authorityText)) {
                                continue;
                            }
                            customUser.addNormalAuthority(authorityText);
                            log.debug("添加角色[{}]相关的普通权限：{}", roleName, authorityText);
                        }
                    }
                }
            }
        }
        return customUser;
    }
}
