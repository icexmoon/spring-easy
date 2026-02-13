package cn.icexmoon.springeasy.security.entity;

import cn.hutool.core.util.BooleanUtil;
import cn.icexmoon.springeasy.security.util.AuthorityUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static cn.icexmoon.springeasy.security.entity.UserAuthority.ROLE_PREFIX;

/**
 * @ClassName User
 * @Description Spring Security 默认的用户实体结构
 * @Author icexmoon@qq.com
 * @Date 2026/1/5 11:03
 * @Version 1.0
 */
@Data
@TableName("se_user")
public class CustomUser implements UserDetails, CredentialsContainer {
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    @TableField(exist = false)
    private SequencedSet<GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (grantedAuthorities == null){
            return List.of();
        }
        return grantedAuthorities;
    }

    private void addGrantedAuthority(GrantedAuthority grantedAuthority){
        if (grantedAuthorities == null){
            grantedAuthorities = new LinkedHashSet<>();
        }
        grantedAuthorities.add(grantedAuthority);
    }

    /**
     * 为用户添加被授予的权限
     * @param grantedAuthority 权限
     */
    private void addGrantedAuthority(String grantedAuthority){
        addGrantedAuthority(new SimpleGrantedAuthority(grantedAuthority));
    }

    /**
     * 为用户添加角色权限
     * @param roleName 角色名称
     */
    public void addRoleAuthority(String roleName){
        addGrantedAuthority(ROLE_PREFIX + roleName);
    }


    /**
     * 为用户添加普通权限
     * @param authority 权限
     */
    public void addNormalAuthority(String authority){
        addGrantedAuthority(authority);
    }

    @Override
    public boolean isEnabled() {
        return BooleanUtil.isTrue(enabled);
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    public boolean hasRole(@NonNull String roleName){
        String roleFlag = ROLE_PREFIX + roleName;
        if (grantedAuthorities == null){
            return false;
        }
        for (GrantedAuthority authority : grantedAuthorities) {
            if (roleFlag.equals(authority.getAuthority())){
                return true;
            }
        }
        return false;
    }

    public List<String> getRoles(){
        if (grantedAuthorities == null){
            return List.of();
        }
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : grantedAuthorities) {
            if (authority == null){
                continue;
            }
            String authorityText = authority.getAuthority();
            if (AuthorityUtil.isRoleAuthority(authorityText)){
                roles.add(AuthorityUtil.getRoleName(authorityText));
            }
        }
        return roles;
    }
}
