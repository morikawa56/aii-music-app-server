package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Album;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;

public interface AlbumService {
    Album findByAid(Integer aid);

    void addAlbum(Album album);

    PageBean<Album> searchAlbum(Integer pageNum, Integer pageSize, String albumname, Boolean mode);

    void updateAlbumInfo(Album album);

    void updateAlbumAvatar(Integer aid, String albumAvatar);

    void deleteAlbum(Integer aid);
}
