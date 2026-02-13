package cn.icexmoon.springeasy.security.entity.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @ClassName UserDTO
 * @Description 脱敏后的用户信息
 * @Author icexmoon@qq.com
 * @Date 2026/1/16 14:53
 * @Version 1.0
 */
@Getter
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private Boolean enabled;
}
