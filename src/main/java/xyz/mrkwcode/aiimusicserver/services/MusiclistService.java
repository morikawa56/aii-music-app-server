package xyz.mrkwcode.aiimusicserver.services;


import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;

public interface MusiclistService {
    void addMusiclist(String musiclistname);

    void updateMusiclist(Integer mlid, String avatarUrl, String introduction, String style);

    void searchMusiclistByname(String musiclistname);

    void updateMusiclistname(Integer mlid, String musiclistname);

    PageBean<Musiclist> listedMusiclist(Integer pageNum, Integer pageSize, Integer mlid, Integer uid, String musiclistname, String style, Boolean mode);

    PageBean<Music> getMusicFromList(Integer pageNum, Integer pageSize, Integer mlid);
}
