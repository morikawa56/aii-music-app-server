package xyz.mrkwcode.aiimusicserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.MusicReq;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;
import xyz.mrkwcode.aiimusicserver.pojos.UnshelveReq;
import xyz.mrkwcode.aiimusicserver.services.CreatorService;
import xyz.mrkwcode.aiimusicserver.services.MusicService;
import xyz.mrkwcode.aiimusicserver.utils.TcosUtil.TcosUtil;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/music")
@Validated
@ResponseResult
public class MusicController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private CreatorService creatorService;

    private static final String COS_MRES_PATH = "aii-music/musicres/";
    private static final String COS_MAVATAR_PATH = "aii-music/musicavatar/";

    @PostMapping
    public void addMusic(HttpServletRequest req, // 音乐信息主干
                         @RequestParam("res") MultipartFile resource,
                         @RequestParam("musicAvatar") MultipartFile musicAvatar) throws IOException {

        if(req.getParameter("musicname").isEmpty() || req.getParameter("musicname").matches("\s")) throw new UniverCustomException(500, "请填写音乐名称！");
        if(req.getParameter("creator").isEmpty() || req.getParameter("creator").matches("\s")) throw new UniverCustomException(500, "请填写创作者信息！");
        if(req.getParameter("album").isEmpty() || req.getParameter("album").matches("\s")) throw new UniverCustomException(500, "请填写专辑信息！");
        if(req.getParameter("publishedTime").isEmpty() || req.getParameter("publishedTime").matches("\s")) throw new UniverCustomException(500, "请填写发行日期！");

        // 音乐基础信息
        String musicname = req.getParameter("musicname").trim();
        String creator = req.getParameter("creator").trim();
        String album = req.getParameter("album").trim();
        String introduction = req.getParameter("introduction").trim();

        // 字符串timestamp转Timestamp类型
        String publishedTimeStr = req.getParameter("publishedTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochSecond(Long.parseLong(publishedTimeStr));
        String publishedTimestamp = formatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        Timestamp publishedTime = Timestamp.valueOf(publishedTimestamp);

        if(resource.isEmpty()) throw new UniverCustomException(500, "音乐上传失败！");

        // 文件上传
        String resOriginalFilename = resource.getOriginalFilename();
        String resFilename = resOriginalFilename.substring(0, resOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + resOriginalFilename.substring(resOriginalFilename.lastIndexOf("."));
        String resUrl = TcosUtil.uploadFile(COS_MRES_PATH, resFilename, resource.getInputStream());
        String avatarUrl = "";
        if(!musicAvatar.isEmpty()) {
            String avatarOriginalFilename = musicAvatar.getOriginalFilename();
            String avatarFilename = avatarOriginalFilename.substring(0, avatarOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + avatarOriginalFilename.substring(avatarOriginalFilename.lastIndexOf("."));
            avatarUrl = TcosUtil.uploadFile(COS_MAVATAR_PATH, avatarFilename, musicAvatar.getInputStream());
        }

        Music music = new Music();
        music.setMusicname(musicname);
        music.setCreator(creator);
        music.setAlbum(album);
        music.setIntroduction(introduction);
        music.setResUrl(resUrl);
        music.setPublishedTime(publishedTime);
        if(!avatarUrl.trim().isEmpty()) music.setMusicAvatar(avatarUrl);

        musicService.addMusic(music);
    }

    @GetMapping
    public PageBean<Music> getMusicList(Integer pageNum,@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String musicname, @RequestParam(required = false) String creator, @RequestParam(required = false) Boolean mode) {
        Integer cid = creatorService.findByCreatorName(creator).getCid();
        // System.out.println(cid);
        return musicService.getMusicList(pageNum, Objects.requireNonNullElse(pageSize, 20), musicname, String.valueOf(cid), Objects.requireNonNullElse(mode, false));
    }

    @PutMapping
    public void updateMusicInfo(@RequestBody MusicReq musicReq) {
        Music music = new Music();
        music.setMid(musicReq.getMid());
        music.setMusicname(musicReq.getMusicname());
        music.setCreator(musicReq.getCreator());
        music.setIntroduction(musicReq.getIntroduction());
        music.setLyric(musicReq.getLyric());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochSecond(Long.parseLong(musicReq.getPublishedTime()));
        String publishedTimestamp = formatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        Timestamp publishedTimetsp = Timestamp.valueOf(publishedTimestamp);
        System.out.println(publishedTimestamp);
        music.setPublishedTime(publishedTimetsp);
        musicService.updateMusicInfo(music);
    }

    @PutMapping("/res")
    public String updateMusicRes(@RequestParam("mid") Integer mid,@RequestParam("file") MultipartFile file) throws IOException {
        Music music = musicService.findByMid(mid);
        String oldUrl = music.getResUrl();
        TcosUtil.removeObject(oldUrl);

        String resOriginalFilename = file.getOriginalFilename();
        String resFilename = null;
        if (resOriginalFilename != null) {
            resFilename = resOriginalFilename.substring(0, resOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + resOriginalFilename.substring(resOriginalFilename.lastIndexOf("."));
        }
        String resUrl = TcosUtil.uploadFile(COS_MRES_PATH, resFilename, file.getInputStream());
        musicService.updateMusicRes(mid, resUrl);
        return resUrl;
    }

    @PutMapping("/avatar")
    public String updateMusicAvatar(@RequestParam("mid") Integer mid,@RequestParam("file") MultipartFile file) throws IOException {
        Music music = musicService.findByMid(mid);
        String oldUrl = music.getMusicAvatar();
        TcosUtil.removeObject(oldUrl);

        String resOriginalFilename = file.getOriginalFilename();
        String resFilename = null;
        if (resOriginalFilename != null) {
            resFilename = resOriginalFilename.substring(0, resOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + resOriginalFilename.substring(resOriginalFilename.lastIndexOf("."));
        }
        String resUrl = TcosUtil.uploadFile(COS_MAVATAR_PATH, resFilename, file.getInputStream());
        musicService.updateMusicAvatar(mid, resUrl);
        return resUrl;
    }

    @DeleteMapping("/unshelve")
    public void unshelve(@RequestBody UnshelveReq unshelveReq) {
        Integer mid = unshelveReq.getMid();
        Boolean type = unshelveReq.getType();
        if(type) {
            musicService.unshelveMusic(mid);
        } else {
            Music music = musicService.findByMid(mid);
            ArrayList<String> urls = new ArrayList<>();
            urls.add(music.getMusicAvatar());
            urls.add(music.getResUrl());
            TcosUtil.removeObject(urls);
            musicService.deleteMusic(mid);
        }
    }

    @PatchMapping("/shelve")
    public void shelve(@RequestParam Integer mid) {
        musicService.shelveMusic(mid);
    }
}
