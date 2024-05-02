package xyz.mrkwcode.aiimusicserver.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.CreatorMapper;
import xyz.mrkwcode.aiimusicserver.DAOs.MusicMapper;
import xyz.mrkwcode.aiimusicserver.pojos.Favourite;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.MusicMap;
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
    @Autowired
    private CreatorMapper creatorMapper;

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
        List<Music> ms;
        if(mode) {
            Map<String,Object> map = ThreadLocalUtil.get();
            Integer userId = (Integer) map.get("uid");
            Integer cid = creatorMapper.ifExistFindByUid(userId).getCid();
            ms = musicMapper.getMusicListCreatorId(musicname,creator,cid);
        } else {
            ms = musicMapper.getMusicListDefault(musicname,creator);
        }
        Page<Music> p = (Page<Music>) ms;
        pgb.setTotal(p.getTotal());
        pgb.setItems(p.getResult());
        return pgb;
    }

    @Override
    public void updateMusicInfo(Music music) {
        musicMapper.updateMusicInfo(music);
    }

    @Override
    public void updateMusicRes(Integer mid, String resUrl) {
        musicMapper.updateMusicRes(mid, resUrl);
    }

@Override
    public void updateMusicAvatar(Integer mid, String musicAvatar) {
        musicMapper.updateMusicAvatar(mid, musicAvatar);
    }

    @Override
    public void deleteMusic(Integer mid) {
        musicMapper.deleteMusic(mid);
    }

    @Override
    public void unshelveMusic(Integer mid) {
        musicMapper.setshelveMusic(mid, 1);
    }

    @Override
    public void shelveMusic(Integer mid) {
        musicMapper.setshelveMusic(mid, 0);
    }

    @Override
    public void listed(Integer mid, Integer mlid) {
        musicMapper.musicListed(mid, mlid);
    }

    @Override
    public Integer searchListedMusicRecord(Integer mid, Integer mlid) {
        MusicMap musicMap = musicMapper.searchListedMusicRecord(mid, mlid);
        return musicMap.getMapid();
    }

    @Override
    public void deleteMusicMap(Integer mapid) {
        musicMapper.deleteMusicMap(mapid);
    }

    @Override
    public void musicFavourite(Integer mid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        Favourite favourite = new Favourite();
        favourite.setUid(uid);
        favourite.setIsMusiclist(false);
        favourite.setMid(mid);
        musicMapper.musicFavourite(favourite);
    }

    @Override
    public void delMusicFavourite(Integer mid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        musicMapper.delMusicFavourite(uid, mid);
    }
}
