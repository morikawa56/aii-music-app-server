package xyz.mrkwcode.aiimusicserver.DAOs;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.mrkwcode.aiimusicserver.pojos.Favourite;

import java.util.List;

@Mapper
public interface FavouriteMapper {
    @Select("Select * from aii_music_favourite")
    List<Favourite> allFavourite();
}
