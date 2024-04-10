package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;

public interface MusicService {
    Music findByMid(Integer mid);

    void addMusic(Music music);

    PageBean<Music> getMusicList(Integer pageNum, Integer pageSize, String musicname, String creator, Boolean mode);
}
