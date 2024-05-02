package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.*;
import xyz.mrkwcode.aiimusicserver.pojos.Comment;
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
    @Update("Update aii_music_user_tasklist set permission=#{permission}, operator=#{operator}, be_operator=#{beOperator}, detail=#{detail}, status=#{status} where task_id=#{taskId}")
    void updateTask(UserTask userTask);
    @Insert("Insert into aii_music_comment(cmid, uid, mid, mlid, content, created_time, updated_time, is_musiclist)" +
            "values(#{cmid}, #{uid}, #{mid}, #{mlid}, #{content}, #{createdTime}, #{updatedTime}, #{isMusiclist})")
    void addComment(Comment comment);
    @Delete("Delete from aii_music_comment where cmid=#{cmid}")
    void removeComment(Integer cmid);

    void like(Comment comment);

    @Select("Select * from aii_music_comment where cmid=#{cmid}")
    Comment findCommentByCmid(Integer cmid);
}
