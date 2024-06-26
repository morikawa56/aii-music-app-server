package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.User;

public interface UserService {
    // 查找用户
    User findByUsername(String username);
    // 注册
    void signup(String username, String password);
    // 更新信息
    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    void updatePermission(Integer uid, String newPermission);

    void forUpdatePermission(String newPermission);

    void forBanUser(Boolean isBanned);

    void banUser(Integer uid, Boolean isBanned);
}
