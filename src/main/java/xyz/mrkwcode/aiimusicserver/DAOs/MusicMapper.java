package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.mrkwcode.aiimusicserver.pojos.Music;

import java.util.List;

@Mapper
public interface MusicMapper {
    @Select("Select * from aii_music_musicinfo where mid=#{mid}")
    Music findByMid(Integer mid);
    @Insert("Insert into aii_music_musicinfo(musicname, creator, album, introduction, music_avatar, published_time, res_url, created_time, updated_time)" +
            "values(#{musicname}, #{creator}, #{album}, #{introduction}, #{musicAvatar}, #{publishedTime}, #{resUrl}, #{createdTime}, #{updatedTime})")
    void addMusic(Music music);

    List<Music> getMusicListDefault(String musicname, String creator);
}
