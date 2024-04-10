package xyz.mrkwcode.aiimusicserver.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.services.MusicService;

@Slf4j
@Service
public class MusicServiceImpl implements MusicService {
    @Override
    public Music findByMid(Integer mid) {
        return null;
    }

    @Override
    public void addMusic(Music music) {

    }
}
