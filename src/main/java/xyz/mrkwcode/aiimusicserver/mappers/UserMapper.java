package xyz.mrkwcode.aiimusicserver.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.mrkwcode.aiimusicserver.pojos.User;

@Mapper
public interface UserMapper {

    @Select("Select * from aii_music_user where username=#{username}")
    User findByUsername(String username);

    @Insert("Insert into aii_music_user(username, nickname, password, permission, profile, created_time, updated_time)" +
            "values(#{username}, #{nickname}, #{password}, #{permission}, #{profile}, #{createdTime}, #{updatedTime})")
    void add(User u);
}
