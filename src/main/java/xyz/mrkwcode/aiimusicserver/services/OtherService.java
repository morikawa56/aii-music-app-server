package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.UserTask;

import java.util.List;

public interface OtherService {

    List<UserTask> findAllTaskList(Integer uid);

    UserTask findTaskByTaskId(Integer taskId);

    void banUser(UserTask userTask);
}
