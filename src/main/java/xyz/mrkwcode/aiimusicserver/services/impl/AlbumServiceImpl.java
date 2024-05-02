package xyz.mrkwcode.aiimusicserver.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mrkwcode.aiimusicserver.DAOs.AlbumMapper;
import xyz.mrkwcode.aiimusicserver.DAOs.CreatorMapper;
import xyz.mrkwcode.aiimusicserver.pojos.Album;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;
import xyz.mrkwcode.aiimusicserver.services.AlbumService;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;
import xyz.mrkwcode.aiimusicserver.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private CreatorMapper creatorMapper;

    @Override
    public Album findByAid(Integer aid) {
        return albumMapper.findByAid(aid);
    }

    @Override
    public void addAlbum(Album album) {
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp timestamp = Timestamp.valueOf(now);
        album.setCreatedTime(timestamp);
        album.setUpdatedTime(timestamp);
        albumMapper.addAlbum(album);
    }

    @Override
    public PageBean<Album> searchAlbum(Integer pageNum, Integer pageSize, String albumname, Boolean mode) {
        PageBean<Album> pgb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Album> as;
        if(mode) {
            Map<String,Object> map = ThreadLocalUtil.get();
            Integer userId = (Integer) map.get("uid");
            Integer cid = creatorMapper.ifExistFindByUid(userId).getCid();
            as = albumMapper.searchAlbumByCreatorId(albumname,cid);
        } else {
            as = albumMapper.searchAlbumDefault(albumname);
        }
        Page<Album> p = (Page<Album>) as;
        pgb.setTotal(p.getTotal());
        pgb.setItems(p.getResult());
        return pgb;
    }

    @Override
    public void updateAlbumInfo(Album album) {
        albumMapper.updateAlbumInfo(album);
    }

    @Override
    public void updateAlbumAvatar(Integer aid, String albumAvatar) {
        albumMapper.updateAlbumAvatar(aid, albumAvatar);
    }

    @Override
    public void deleteAlbum(Integer aid) {
        albumMapper.deleteAlbum(aid);
    }
}
