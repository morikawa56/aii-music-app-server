package xyz.mrkwcode.aiimusicserver.controllers;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.mrkwcode.aiimusicserver.annos.Gender;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.Result;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.services.UserService;
import xyz.mrkwcode.aiimusicserver.utils.JwtUtil;
import xyz.mrkwcode.aiimusicserver.utils.Md5Util;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user")
@Validated
@ResponseResult
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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

    @PostMapping("/login")
    public String login(@Pattern(regexp = "^(?!_)(?!.*?_$)\\w{8,20}$") /* 8-20位大小写字母及数字下划线 */ String username,
                      @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$") String password) {
        // 查询是否有同名用户
        User loginUser = userService.findByUsername(username);
        // 判断该用户是否存在
        if(loginUser == null) throw new UniverCustomException(500, "用户名错误");
        // 判断密码是否正确
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", loginUser.getUid());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            // 把token存储到redis中
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            if(operations.get("AiiMusic:" + "usertoken-" + loginUser.getUid()) != null) {
                operations.getOperations().delete("AiiMusic:" + "usertoken-" + loginUser.getUid());
            }
            operations.set("AiiMusic:" + "usertoken-" + loginUser.getUid(), token, 1, TimeUnit.HOURS);
            return token;
        }
        throw new UniverCustomException(500, "密码错误");
    }
    @GetMapping
    public User getInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return user;
    }

    @PutMapping
    public void update(@RequestBody @Validated(User.Update.class) User user) {
        userService.update(user);
    }

    @PatchMapping("/avatar")
    public void updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
    }

    @PatchMapping("/password")
    public void updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token) {
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            throw new UniverCustomException(500, "缺少必要的参数");
        }
        // 验证新密码格式
        if(!newPwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$")) {
            throw new UniverCustomException(500, "新密码格式不正确");
        }

        // 原密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if(!loginUser.getPassword().equals((Md5Util.getMD5String(oldPwd)))) {
            throw new UniverCustomException(500, "原密码填写不正确");
        }

        // newPwd和rePwd是否一样
        if(!rePwd.equals(newPwd)) {
            throw new UniverCustomException(500, "两次填写的新密码不一样");
        }

        // 调用service完成密码更新
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete("AiiMusic:" + "usertoken-" + loginUser.getUid());
        userService.updatePwd(newPwd);
    }

    @PatchMapping("/permission")
    public void updatePermission(@RequestParam Integer uid, @RequestParam String newPermission) {
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if(loginUser.getPermission().equals("admin")) {
            userService.updatePermission(uid, newPermission);
        } else {
            if(!uid.equals(loginUser.getUid())) throw new UniverCustomException(500, "普通用户请进行自己账号的权限申请");
            else userService.forUpdatePermission(newPermission);
        }
    }


    @PatchMapping("/ban")
    public void banUser(@RequestParam Integer uid, @RequestParam Boolean isBanned) {
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if(!loginUser.getPermission().equals("admin")) {
            userService.forBanUser(isBanned);
        }
        else {
            userService.banUser(uid, isBanned);
        }
    }

    @PostMapping("/logout")
    public void logout() {
        // Map<String,Object> map = ThreadLocalUtil.get();
        // String username = (String) map.get("username");
        // User loginUser = userService.findByUsername(username);
        // ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        // operations.getOperations().delete("AiiMusic:" + "usertoken-" + loginUser.getUid());
        ThreadLocalUtil.remove();
    }
}
