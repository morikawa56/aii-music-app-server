package xyz.mrkwcode.aiimusicserver.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.pojos.Result;
import xyz.mrkwcode.aiimusicserver.pojos.User;

@RestController
@RequestMapping("/api/user")
@Validated
@ResponseResult
public class UserController {

    @GetMapping
    public User testClass() {
        User user = new User();
        user.setUsername("admintest");
        user.setPassword("Ajm991019");
        user.setUid(1);
        return user;
    }
}
