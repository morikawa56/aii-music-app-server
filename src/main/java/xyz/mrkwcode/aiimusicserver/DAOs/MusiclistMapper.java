package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.*;
import xyz.mrkwcode.aiimusicserver.pojos.Favourite;
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

    @Select("Select * from aii_music_musiclist where mlid=#{mlid}")
    Musiclist findByMlid(Integer mlid);

    @Insert("Insert into aii_music_favourite(uid, is_musiclist, mlid, created_time, updated_time)" +
            "values(#{uid}, #{isMusiclist}, #{mlid}, now(), now())")
    void favMusiclist(Favourite favourite);

    @Delete("Delete from aii_music_favourite where mlid=#{mlid} and uid=#{uid}")
    void delFavMusiclist(Integer uid, Integer mlid);

    @Select("SELECT * from aii_music_musiclist where find_in_set(#{musiclistname}, musiclistname) ")
    List<Musiclist> searchMusiclistByname(String musiclistname);

    @Delete("Delete from aii_music_musiclist where mlid=#{mlid}")
    void deleteMusiclist(Integer mlid);

    @Delete("Delete from aii_music_favourite where is_musiclist=1 and mlid=#{mlid}")
    void delFavCausedNone(Integer mlid);

    @Delete("Delete from aii_music_musictolist where mlid=#{mlid}")
    void cancelMusicToMusiclistByMlid(Integer mlid);
}
