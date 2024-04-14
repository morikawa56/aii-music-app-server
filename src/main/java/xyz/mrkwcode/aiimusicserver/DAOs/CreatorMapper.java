package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.mrkwcode.aiimusicserver.pojos.Creator;

@Mapper
public interface CreatorMapper {
    @Select("SELECT * FROM aii_music_creator WHERE creatorname=#{creatorname}")
    Creator findByCreatorName(String creatorname);
}
