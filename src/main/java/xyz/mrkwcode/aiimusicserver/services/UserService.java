package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.User;

public interface UserService {
    // 查找用户
    User findByUsername(String username);
    // 注册
    void signup(String username, String password);
}
