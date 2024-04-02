package xyz.mrkwcode.aiimusicserver.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;

import java.sql.Timestamp;

@Mapper
public interface UserMapper {

    @Select("Select * from aii_music_user where username=#{username}")
    User findByUsername(String username);

    @Insert("Insert into aii_music_user(username, nickname, password, permission, profile, created_time, updated_time)" +
            "values(#{username}, #{nickname}, #{password}, #{permission}, #{profile}, #{createdTime}, #{updatedTime})")
    void add(User u);

    @Update("Update aii_music_user set nickname=#{nickname},email_address=#{emailAddress},phone_number=#{phoneNumber},profile=#{profile},updated_time=#{updatedTime} where uid=#{uid}")
    void update(User user);

    @Update("Update aii_music_user set avatar_url=#{avatarUrl},updated_time=#{updatedTime} where uid=#{uid}")
    void updateAvatar(String avatarUrl, Integer uid, Timestamp updatedTime);

    @Update("Update aii_music_user set password=#{md5String},updated_time=#{updatedTime} where uid=#{uid}")
    void updatePwd(String md5String, Integer uid, Timestamp updatedTime);

    @Update("Update aii_music_user set permission=#{newPermission} where uid=#{uid}")
    void updatePermission(Integer uid, String newPermission);

    @Insert("Insert into aii_music_user_tasklist(task_id, permission, be_operator,detail)" +
            "values(#{taskId}, #{permission}, #{beOperator}, #{detail})")
    void forUpdatePermission(UserTask userTask);
}
