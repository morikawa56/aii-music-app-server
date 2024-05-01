package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Comment;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;

import java.util.List;

public interface OtherService {

    List<UserTask> findAllTaskList(Integer uid);

    UserTask findTaskByTaskId(Integer taskId);

    void banUser(UserTask userTask);

    void changePermission(UserTask userTask);

    void addComment(Comment comment);

    void removeComment(Integer cmid);

    void like(Comment comment);

    Comment findCommentByCmid(Integer cmid);
}
