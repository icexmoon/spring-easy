package cn.icexmoon.springeasy.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icexmoon.springeasy.test.entity.User;
import cn.icexmoon.springeasy.test.service.UserService;
import cn.icexmoon.springeasy.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 70748
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-07-09 14:10:30
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public List<User> getUsers(User.Sex sex) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sex", sex.getValue());
        return this.list(queryWrapper);
    }
}




