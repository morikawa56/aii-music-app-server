package xyz.mrkwcode.aiimusicserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.OtherMapper;
import xyz.mrkwcode.aiimusicserver.DAOs.UserMapper;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;
import xyz.mrkwcode.aiimusicserver.services.OtherService;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;
import xyz.mrkwcode.aiimusicserver.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired
    private OtherMapper otherMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<UserTask> findAllTaskList(Integer uid) {
        Integer loginUid = null;
        if(uid == null) {
            Map<String, Object> map = ThreadLocalUtil.get();
            loginUid = (Integer) map.get("uid");
        }
        User loginUser = userMapper.findByUid(Objects.requireNonNullElse(uid, loginUid));
        List<UserTask> adminTasks = null;
        if(loginUser.getPermission().equals("admin")) {
            adminTasks = otherMapper.findAdminTask();
        }
        List<UserTask> userTasks = otherMapper.findUserTasks(uid);
        if(adminTasks != null) {
            Collections.addAll(userTasks, adminTasks.toArray(new UserTask[0]));
            Collections.sort(userTasks, UserTask::compareByTaskId);
        }
        return userTasks;
    }

    @Override
    public UserTask findTaskByTaskId(Integer taskId) {
        return otherMapper.findTaskByTaskId(taskId);
    }

    @Override
    public void banUser(UserTask userTask) {
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp time = Timestamp.valueOf(now);
        userMapper.banUser(userTask.getBeOperator(), true, time);
        userTask.setStatus("FIN"); // FIN——已完成
        Map<String, Object> userTaskMap = JSONObject.parseObject(userTask.getDetail());
        long nowTime = System.currentTimeMillis();
        userTaskMap.replace("updated_time", nowTime);
        userTaskMap.replace("finished_time", nowTime);
        String userTaskJson = JSONObject.toJSONString(userTaskMap,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);
        userTask.setDetail(userTaskJson);
        otherMapper.updateTask(userTask);
    }
}
