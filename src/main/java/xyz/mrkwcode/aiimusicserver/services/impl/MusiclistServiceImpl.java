package xyz.mrkwcode.aiimusicserver.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.MusiclistMapper;
import xyz.mrkwcode.aiimusicserver.pojos.Favourite;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;
import xyz.mrkwcode.aiimusicserver.services.MusiclistService;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class MusiclistServiceImpl implements MusiclistService {
    @Autowired
    private MusiclistMapper musiclistMapper;

    @Override
    public void addMusiclist(String musiclistname) {
        Musiclist musiclist = new Musiclist();
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        musiclist.setMusiclistname(musiclistname);
        musiclist.setUid(uid);
        musiclistMapper.addMusiclist(musiclist);
    }

    @Override
    public void updateMusiclist(Integer mlid, String avatarUrl, String introduction, String style) {
        Musiclist musiclist = new Musiclist();
        musiclist.setMlid(mlid);
        musiclist.setMusiclistPic(avatarUrl);
        musiclist.setIntroduction(introduction);
        musiclist.setStyle(style);
        musiclistMapper.updateMusiclist(musiclist);
    }

    @Override
    public List<Musiclist> searchMusiclistByname(String musiclistname) {
        List<Musiclist> musiclists = musiclistMapper.searchMusiclistByname(musiclistname);
        return musiclists;
    }

    @Override
    public Musiclist searchMusiclistById(Integer mlid) {
        Musiclist musiclist = musiclistMapper.findByMlid(mlid);
        return musiclist;
    }

    @Override
    public void updateMusiclistname(Integer mlid, String musiclistname) {
        musiclistMapper.updateMusiclistname(mlid, musiclistname);
    }

    @Override
    public PageBean<Musiclist> listedMusiclist(Integer pageNum, Integer pageSize, Integer mlid, Integer uid, String musiclistname, String style, Boolean mode) {
        PageBean<Musiclist> pgb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Musiclist> msli;
        if(mode) {
            Map<String,Object> map = ThreadLocalUtil.get();
            Integer userId = Objects.requireNonNullElse(uid, (Integer) map.get("uid"));
            msli = musiclistMapper.listedMusicListUserId(userId, mlid, musiclistname, style);
        } else {
            msli = musiclistMapper.listedMusicListDefault(mlid, musiclistname, style);
        }
        Page<Musiclist> p = (Page<Musiclist>) msli;
        pgb.setTotal(p.getTotal());
        pgb.setItems(p.getResult());
        return pgb;
    }

    @Override
    public PageBean<Music> getMusicFromList(Integer pageNum, Integer pageSize, Integer mlid) {
        PageBean<Music> pgb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Music> ms = musiclistMapper.getMusicFromList(pageNum, pageSize, mlid);
        Page<Music> p = (Page<Music>) ms;
        pgb.setTotal(p.getTotal());
        pgb.setItems(p.getResult());
        return pgb;
    }

    @Override
    public void favMusiclist(Integer mlid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        Favourite favourite = new Favourite();
        favourite.setUid(uid);
        favourite.setIsMusiclist(true);
        favourite.setMlid(mlid);
        musiclistMapper.favMusiclist(favourite);
    }

    @Override
    public void delFavMusiclist(Integer mlid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        musiclistMapper.delFavMusiclist(uid, mlid);
    }

    @Override
    public void deleteMusiclist(Integer mlid) {
        musiclistMapper.delFavCausedNone(mlid);
        musiclistMapper.deleteMusiclist(mlid);
        musiclistMapper.cancelMusicToMusiclistByMlid(mlid);
    }
}
