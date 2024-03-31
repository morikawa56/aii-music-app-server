package xyz.mrkwcode.aiimusicserver.controllers;

import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.Result;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.services.UserService;

@RestController
@RequestMapping("/api/user")
@Validated
@ResponseResult
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public void signup(@Pattern(regexp = "^(?!_)(?!.*?_$)\\w{8,20}$") /* 8-20位大小写字母及数字下划线 */ String username, @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$") String password) {
        // 查询是否有同名用户
        User u = userService.findByUsername(username);
        if(u == null) {
            // 没有占用
            userService.signup(username, password);
        } else {
            // 抛出异常（状态码，报错信息）
            throw new UniverCustomException(500, "用户名已被占用");
        }

    }
    @GetMapping
    public User testClass() {
        User user = new User();
        user.setUsername("admintest");
        user.setPassword("Ajm991019");
        user.setUid(1);
        return user;
    }
}
