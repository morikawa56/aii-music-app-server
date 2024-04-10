package xyz.mrkwcode.aiimusicserver.services.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.MusicMapper;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;
import xyz.mrkwcode.aiimusicserver.services.MusicService;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;
import xyz.mrkwcode.aiimusicserver.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;
    @Override
    public Music findByMid(Integer mid) {
        return musicMapper.findByMid(mid);
    }

    @Override
    public void addMusic(Music music) {
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp timestamp = Timestamp.valueOf(now);
        music.setCreatedTime(timestamp);
        music.setUpdatedTime(timestamp);
        musicMapper.addMusic(music);
    }

    @Override
    public PageBean<Music> getMusicList(Integer pageNum, Integer pageSize, String musicname, String creator, Boolean mode) {
        PageBean<Music> pgb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Integer userId = 0;
        if(mode) {
            Map<String,Object> map = ThreadLocalUtil.get();
            userId = (Integer) map.get("uid");
        } else {
            List<Music> ms = musicMapper.getMusicListDefault(musicname,creator);
        }
    }
}
