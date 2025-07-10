package cn.icexmoon.springeasy.test.service;

import cn.icexmoon.springeasy.test.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName UserServiceTests
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午2:23
 * @Version 1.0
 */
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;


    @Test
    public void testSave(){
        User user = new User();
        user.setName("July");
        user.setSex(User.Sex.FEMALE);
        userService.save(user);
    }

    @Test
    public void testGet(){
        User user = userService.getById(1);
        System.out.println(user);
    }
}
