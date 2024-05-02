package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.*;
import xyz.mrkwcode.aiimusicserver.pojos.Favourite;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.MusicMap;

import java.util.List;

@Mapper
public interface MusicMapper {
    @Select("Select * from aii_music_musicinfo where mid=#{mid}")
    Music findByMid(Integer mid);
    @Insert("Insert into aii_music_musicinfo(musicname, creator, album, introduction, music_avatar, published_time, res_url, created_time, updated_time)" +
            "values(#{musicname}, #{creator}, #{album}, #{introduction}, #{musicAvatar}, #{publishedTime}, #{resUrl}, #{createdTime}, #{updatedTime})")
    void addMusic(Music music);

    List<Music> getMusicListDefault(String musicname, String creator);

    List<Music> getMusicListCreatorId(String musicname, String creator, Integer cid);

    @Update("Update aii_music_musicinfo set musicname=#{musicname},creator=#{creator},lyric=#{lyric},introduction=#{introduction},published_time=#{publishedTime},updated_time=now() where mid=#{mid}")
    void updateMusicInfo(Music music);

    @Update("Update aii_music_musicinfo set res_url=#{resUrl},updated_time=now() where mid=#{mid}")
    void updateMusicRes(Integer mid, String resUrl);

    @Update("Update aii_music_musicinfo set music_avatar=#{musicAvatar},updated_time=now() where mid=#{mid}")
    void updateMusicAvatar(Integer mid, String musicAvatar);

    @Update("Update aii_music_musicinfo set is_unshelved=#{isShelved},updated_time=now() where mid=#{mid}")
    void setshelveMusic(Integer mid, Integer isShelved);

    @Delete("Delete from aii_music_musicinfo where mid=#{mid}")
    void deleteMusic(Integer mid);

    @Insert("Insert into aii_music_musictolist(mid, mlid)" +
            "values(#{mid}, #{mlid})")
    void musicListed(Integer mid, Integer mlid);

    @Select("Select * from aii_music_musictolist where mid=#{mid} and mlid=#{mlid}")
    MusicMap searchListedMusicRecord(Integer mid, Integer mlid);

    @Delete("Delete from aii_music_musictolist where mapid=#{mapid}")
    void deleteMusicMap(Integer mapid);

    @Insert("Insert into aii_music_favourite(uid, is_musiclist, mid, created_time, updated_time)" +
            "values(#{uid}, #{isMusiclist}, #{mid}, now(), now())")
    void musicFavourite(Favourite favourite);

    @Delete("Delete from aii_music_favourite where mid=#{mid} and uid=#{uid}")
    void delMusicFavourite(Integer uid, Integer mid);
}
