package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.*;
import xyz.mrkwcode.aiimusicserver.pojos.Album;

import java.util.List;

@Mapper
public interface AlbumMapper {

    @Select("Select * from aii_music_album where aid=#{aid}")
    Album findByAid(Integer aid);

    @Insert("Insert into aii_music_album(aid, albumname, creator_id, album_avatar, published_time, created_time, updated_time)" +
            "values(#{aid}, #{albumname}, #{creatorId}, #{albumAvatar}, #{publishedTime}, #{createdTime}, #{updatedTime})")
    void addAlbum(Album album);

    @Select("Select * from aii_music_album where position( #{albumname} in albumname ) and json_contains( creator_id, #{cid} )")
    List<Album> searchAlbumByCreatorId(String albumname, Integer cid);

    @Select("Select * from aii_music_album where position( #{albumname} in albumname )")
    List<Album> searchAlbumDefault(String albumname);

    @Update("Update aii_music_album set albumname=#{albumname},creator_id=#{creatorId},introduction=#{introduction},published_time=#{publishedTime},updated_time=now() where aii_music_album.aid=#{aid}")
    void updateAlbumInfo(Album album);

    @Update("Update aii_music_album set album_avatar=#{albumAvatar}, updated_time=now() where aid=#{aid}")
    void updateAlbumAvatar(Integer aid, String albumAvatar);

    @Delete("Delete from aii_music_album where aid=#{aid}")
    void deleteAlbum(Integer aid);
}
