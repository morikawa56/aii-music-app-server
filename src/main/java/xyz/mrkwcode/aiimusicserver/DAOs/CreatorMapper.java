package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.mrkwcode.aiimusicserver.pojos.Creator;

@Mapper
public interface CreatorMapper {
    @Select("SELECT * FROM aii_music_creator WHERE creatorname=#{creatorname}")
    Creator findByCreatorName(String creatorname);

    @Select("SELECT aii_music_creator.cid,aii_music_creator.creatorname,aii_music_creator.gender,aii_music_creator.creator_avatar,aii_music_creator.birth,aii_music_creator.location,aii_music_creator.introduction FROM aii_music_creator LEFT JOIN aii_music_creatortouser ON aii_music_creator.cid = aii_music_creatortouser.cid WHERE aii_music_creatortouser.uid=#{uid}")
    Creator ifExistFindByUid(Integer uid);

    @Insert("Insert into aii_music_creator(creatorname, gender, creator_avatar, birth)" +
            "values(#{creatorname}, #{gender}, #{creatorAvatar}, #{birth})")
    void addCreator(Creator creator);
}
