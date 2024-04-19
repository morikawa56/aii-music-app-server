package xyz.mrkwcode.aiimusicserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.pojos.PageBean;
import xyz.mrkwcode.aiimusicserver.services.MusiclistService;
import xyz.mrkwcode.aiimusicserver.services.RecommendMusiclistService;
import xyz.mrkwcode.aiimusicserver.utils.TcosUtil.TcosUtil;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/musiclist")
@Validated
@ResponseResult
public class MusiclistController {
    @Autowired
    private MusiclistService musiclistService;

    @Autowired
    private RecommendMusiclistService recommendMusiclistService;

    private static final String COS_MLAVATAR_PATH = "aii-music/musiclistavatar/";
    @PostMapping
    public void addMusiclist(@RequestParam String musiclistname) {
        musiclistService.addMusiclist(musiclistname);
    }

    @PutMapping
    public void updateMusiclist(@RequestParam Integer mlid,
                                @RequestParam(required = false) MultipartFile musiclistAvatar,
                                @RequestParam(required = false) String introduction,
                                @RequestParam(required = false) String style) throws IOException {
        String avatarUrl = "";
        if(!musiclistAvatar.isEmpty()) {
            String avatarOriginalFilename = musiclistAvatar.getOriginalFilename();
            String avatarFilename = avatarOriginalFilename.substring(0, avatarOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + avatarOriginalFilename.substring(avatarOriginalFilename.lastIndexOf("."));
            avatarUrl = TcosUtil.uploadFile(COS_MLAVATAR_PATH, avatarFilename,musiclistAvatar.getInputStream());
        }
        musiclistService.updateMusiclist(mlid, avatarUrl, introduction, style);
    }

    @PutMapping("/name")
    public void updateMusiclistname(@RequestParam("mlid") Integer mlid,
                                @RequestParam("musiclistname") String musiclistname)  {
        musiclistService.updateMusiclistname(mlid, musiclistname);
    }

    @GetMapping
    public PageBean<Musiclist> listedMusiclist(Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) Integer mlid,
                                               @RequestParam(required = false) Integer uid,
                                               @RequestParam(required = false) String musiclistname,
                                               @RequestParam(required = false) String style,
                                               @RequestParam(required = false) Boolean mode) {
        PageBean<Musiclist> pgb = musiclistService.listedMusiclist(pageNum, Objects.requireNonNullElse(pageSize, 10), mlid, uid, musiclistname, style, Objects.requireNonNullElse(mode, false));
        return pgb;
    }

    @GetMapping("/detail")
    public PageBean<Music> getMusicFromList(Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            Integer mlid) {
        PageBean<Music> pgb = musiclistService.getMusicFromList(pageNum, Objects.requireNonNullElse(pageSize, 20), mlid);
        return pgb;
    }

    @GetMapping("/recommend/list")
    public List<Musiclist> getRecommendMusiclist() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("uid");
        List<Musiclist> pgb = recommendMusiclistService.recommendMusiclistByFavourite(userId);
        return pgb;
        // return null;
    }

    @PostMapping("/fav")
    public void favMusiclist(@RequestParam Integer mlid) {
        musiclistService.favMusiclist(mlid);
    }

    @DeleteMapping("/fav")
    public void delFavMusiclist(@RequestParam Integer mlid) {
        musiclistService.delFavMusiclist(mlid);
    }

    @GetMapping("/recommend")
    public List<Music> recommendMusiclist() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        return recommendMusiclistService.recommendMusic(uid);
    }
}
