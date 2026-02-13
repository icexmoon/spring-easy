package cn.icexmoon.springeasy.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.icexmoon.springeasy.security.entity.UserAuthority;
import cn.icexmoon.springeasy.security.mapper.UserAuthorityMapper;
import cn.icexmoon.springeasy.security.service.UserAuthorityService;
import cn.icexmoon.springeasy.security.util.AuthorityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserAuthorityServiceImpl
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/19 09:30
 * @Version 1.0
 */
@Service
public class UserAuthorityServiceImpl extends ServiceImpl<UserAuthorityMapper, UserAuthority> implements UserAuthorityService {
    @Override
    public List<UserAuthority> listByUsername(String username) {
        return baseMapper.selectList(new QueryWrapper<UserAuthority>().eq("username", username));
    }

    @Override
    public List<String> getRolesByUsername(String username) {
        List<UserAuthority> userAuthorities = listByUsername(username);
        List<String> roles = new ArrayList<>();
        if (!CollectionUtil.isEmpty(userAuthorities)){
            for (UserAuthority userAuthority : userAuthorities) {
                String authority = userAuthority.getAuthority();
                if (AuthorityUtil.isRoleAuthority( authority)){
                    roles.add(AuthorityUtil.getRoleName(authority));
                }
            }
        }
        return roles;
    }
}
