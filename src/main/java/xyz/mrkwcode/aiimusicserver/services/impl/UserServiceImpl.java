package xyz.mrkwcode.aiimusicserver.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.mappers.UserMapper;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.services.UserService;
import xyz.mrkwcode.aiimusicserver.utils.Md5Util;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;
import xyz.mrkwcode.aiimusicserver.utils.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void signup(String username, String password) {
        // 对密码进行加密
        String md5Password = Md5Util.getMD5String(password);

        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp timestamp = Timestamp.valueOf(now);

        User u = new User();
        u.setUsername(username);
        u.setNickname(username);
        u.setPassword(md5Password);
        u.setPermission("user");
        u.setProfile("{}");
        u.setCreatedTime(timestamp);
        u.setUpdatedTime(timestamp);
        // 向数据库添加记录
        userMapper.add(u);
    }

    @Override
    public void update(User user) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        user.setUid(uid);
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        user.setUpdatedTime(Timestamp.valueOf(now));
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp updatedTime = Timestamp.valueOf(now);
        userMapper.updateAvatar(avatarUrl, uid, updatedTime);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp updatedTime = Timestamp.valueOf(now);
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), uid, updatedTime);
    }
}
