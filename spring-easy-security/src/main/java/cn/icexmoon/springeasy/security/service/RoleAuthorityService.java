package cn.icexmoon.springeasy.security.service;

import cn.icexmoon.springeasy.security.entity.RoleAuthority;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;

import java.util.List;
import java.util.SortedSet;

/**
 * @ClassName RoleAuthorityService
 * @Description 角色权限服务
 * @Author icexmoon@qq.com
 * @Date 2026/1/19 08:35
 * @Version 1.0
 */
public interface RoleAuthorityService extends IService<RoleAuthority> {
    /**
     * 根据角色获取权限
     * @param role 角色
     * @return 权限
     */
    List<RoleAuthority> listByRole(String role);

    /**
     * 根据角色列表获取权限
     * @param roles 角色列表
     * @return 权限列表
     */
    List<RoleAuthority> listByRole(@NonNull List<String> roles);

    /**
     * 根据角色列表获取权限
     * @param roles 角色列表
     * @return 权限列表
     */
    SortedSet<String> getAuthoritiesByRole(List<String> roles);
}
