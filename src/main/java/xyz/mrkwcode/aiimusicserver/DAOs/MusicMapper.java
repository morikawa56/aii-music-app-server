package xyz.mrkwcode.aiimusicserver.DAOs;

import xyz.mrkwcode.aiimusicserver.pojos.Music;

public interface MusicMapper {
    Music findByMid(Integer mid);
    void addMusic(Music music);
}
