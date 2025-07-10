package cn.icexmoon.springeasy.test.controller;

import cn.icexmoon.springeasy.test.entity.User;
import cn.icexmoon.springeasy.test.service.UserService;
import cn.icexmoon.springeasy.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 下午2:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getUsers(@RequestParam User.Sex sex){
        return userService.getUsers(sex);
    }

    @PostMapping("/add")
    public Result<Long> addUser(@RequestBody User user){
        log.info(user.toString());
        userService.save(user);
        return Result.success(user.getId());
    }
}
