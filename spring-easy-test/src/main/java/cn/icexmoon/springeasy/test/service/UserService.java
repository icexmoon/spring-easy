package cn.icexmoon.springeasy.test.service;

import cn.icexmoon.springeasy.test.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 70748
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-07-09 14:10:30
*/
public interface UserService extends IService<User> {

    List<User> getUsers(User.Sex sex);
}
