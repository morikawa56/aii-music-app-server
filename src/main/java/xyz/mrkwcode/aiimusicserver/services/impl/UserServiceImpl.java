package xyz.mrkwcode.aiimusicserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.UserMapper;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;
import xyz.mrkwcode.aiimusicserver.services.UserService;
import xyz.mrkwcode.aiimusicserver.utils.Md5Util;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;
import xyz.mrkwcode.aiimusicserver.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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
    public User findByUid(Integer uid) {
        return userMapper.findByUid(uid);
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
        u.setIsBanned(false);
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

    @Override
    public void updatePermission(Integer uid, String newPermission) {
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp updatedTime = Timestamp.valueOf(now);
        userMapper.updatePermission(uid,newPermission,updatedTime);
    }

    @Override
    public void forUpdatePermission(String newPermission) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp time = Timestamp.valueOf(now);
        UserTask userTask = new UserTask();
        userTask.setBeOperator(uid);
        userTask.setPermission("admin");
        userTask.setStatus("TOC"); // TOC——等待批准
        Map<String,Object> userTaskMap = new HashMap<String,Object>();
        userTaskMap.put("operation", "updatePermission");
        userTaskMap.put("update-to", newPermission);
        userTaskMap.put("created_time", time);
        userTaskMap.put("updated_time", null);
        userTaskMap.put("finished_time", null);
        String userTaskJson = JSONObject.toJSONString(userTaskMap,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);
        log.info("userTaskMap: " + userTaskMap);
        userTask.setDetail(userTaskJson);
        userMapper.forUpdatePermission(userTask);
    }

    @Override
    public void forBanUser(Boolean isBanned) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp time = Timestamp.valueOf(now);
        UserTask userTask = new UserTask();
        userTask.setBeOperator(uid);
        userTask.setPermission("admin");
        userTask.setStatus("TOC"); // TOC——等待批准
        Map<String,Object> userTaskMap = new HashMap<String,Object>();
        userTaskMap.put("operation", "banUser");
        userTaskMap.put("update-to", isBanned);
        userTaskMap.put("created_time", time);
        userTaskMap.put("updated_time", null);
        userTaskMap.put("finished_time", null);
        String userTaskJson = JSONObject.toJSONString(userTaskMap,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);
        log.info("userTaskMap: " + userTaskMap);
        userTask.setDetail(userTaskJson);
        userMapper.forBanUser(userTask);
    }

    @Override
    public void banUser(Integer uid, Boolean isBanned) {
        User loginUser = userMapper.findByUid(uid);
        if(loginUser == null) throw new UniverCustomException(500, "该用户不存在");
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp updatedTime = Timestamp.valueOf(now);
        userMapper.banUser(uid,isBanned,updatedTime);
    }
}
