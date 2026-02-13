package cn.icexmoon.springeasy.security.service;

import cn.icexmoon.springeasy.security.entity.CustomUser;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;

/**
 * @ClassName CustomUserService
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2026/1/16 18:04
 * @Version 1.0
 */
public interface CustomUserService extends IService<CustomUser>{
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    CustomUser getByUsername(@NonNull String username);
}
