package xyz.mrkwcode.aiimusicserver.services;

import xyz.mrkwcode.aiimusicserver.pojos.Music;

public interface MusicService {
    Music findByMid(Integer mid);

    void addMusic(Music music);
}
