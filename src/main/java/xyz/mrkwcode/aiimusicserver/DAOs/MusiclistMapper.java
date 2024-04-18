package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;

import java.util.List;

@Mapper
public interface MusiclistMapper {
    @Insert("Insert into aii_music_musiclist(musiclistname, uid, created_time, updated_time)" +
            "values(#{musiclistname}, #{uid}, now(), now())")
    void addMusiclist(Musiclist musiclist);

    @Update("Update aii_music_musiclist set musiclist_pic=#{musiclistPic},introduction=#{introduction},style=#{style},updated_time=now() where mlid=#{mlid}")
    void updateMusiclist(Musiclist musiclist);

    @Update("Update aii_music_musiclist set musiclistname=#{musiclistname},updated_time=now() where mlid=#{mlid}")
    void updateMusiclistname(Integer mlid, String musiclistname);

    List<Musiclist> listedMusicListUserId(Integer uid, Integer mlid, String musiclistname, String style);

    List<Musiclist> listedMusicListDefault(Integer mlid, String musiclistname, String style);

    @Select("Select aii_music_musicinfo.mid, aii_music_musicinfo.musicname, aii_music_musicinfo.creator, aii_music_musicinfo.album, aii_music_musicinfo.introduction, aii_music_musicinfo.music_avatar, aii_music_musicinfo.lyric, aii_music_musicinfo.is_unshelved, aii_music_musicinfo.published_time, aii_music_musicinfo.res_url, aii_music_musicinfo.created_time, aii_music_musicinfo.updated_time " +
            "from aii_music_musicinfo left join aii_music_musictolist on aii_music_musicinfo.mid=aii_music_musictolist.mid where aii_music_musictolist.mlid=#{mlid}")
    List<Music> getMusicFromList(Integer pageNum, Integer pageSize, Integer mlid);
}
