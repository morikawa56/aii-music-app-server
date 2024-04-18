package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.mrkwcode.aiimusicserver.pojos.MusicToList;

import java.util.List;

@Mapper
public interface MusicToListMapper {
    @Select("Select * from aii_music_musictolist")
    List<MusicToList> allMusicToList();
}
