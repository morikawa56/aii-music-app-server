package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;

import java.util.List;

@Mapper
public interface OtherMapper {
    @Select("Select * from aii_music_user_tasklist where permission='admin'")
    List<UserTask> findAdminTask();
    @Select("Select * from aii_music_user_tasklist where operator=#{uid}")
    List<UserTask> findUserTasks(Integer uid);
    @Select("Select * from aii_music_user_tasklist where task_id=#{taskId}")
    UserTask findTaskByTaskId(Integer taskId);
    @Update("Update aii_music_user_tasklist set task_id=#{taskId}, permission=#{permission}, operator=#{operator}, be_operator=#{beOperator}, detail=#{detail}, status=#{status}")
    void updateTask(UserTask userTask);
}
