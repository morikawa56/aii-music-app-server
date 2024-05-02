package xyz.mrkwcode.aiimusicserver.controllers;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.*;
import xyz.mrkwcode.aiimusicserver.services.AlbumService;
import xyz.mrkwcode.aiimusicserver.services.CreatorService;
import xyz.mrkwcode.aiimusicserver.services.UserService;
import xyz.mrkwcode.aiimusicserver.utils.TcosUtil.TcosUtil;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/album")
@ResponseResult
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Autowired
    private CreatorService creatorService;

    private static final String COS_AAVATAR_PATH = "aii-music/albumavatar/";

    @PostMapping
    public void addAlbum(@RequestParam String albumname,
                         @RequestParam String creator,
                         @RequestParam("albumAvatar") MultipartFile albumAvatar,
                         @RequestParam("publishTime") String publishedTimeStr) throws IOException {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin")  && !loginUser.getPermission().equals("creator")) throw new UniverCustomException(500, "普通用户没有权限创建专辑");
        if(loginUser.getIsBanned() == true) throw new UniverCustomException(500, "用户被封禁不能创建专辑");

        // 字符串timestamp转Timestamp类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochSecond(Long.parseLong(publishedTimeStr));
        String publishedTimestamp = formatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        Timestamp publishedTime = Timestamp.valueOf(publishedTimestamp);

        String avatarUrl = "";
        if(!albumAvatar.isEmpty()) {
            String avatarOriginalFilename = albumAvatar.getOriginalFilename();
            String avatarFilename = avatarOriginalFilename.substring(0, avatarOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + avatarOriginalFilename.substring(avatarOriginalFilename.lastIndexOf("."));
            avatarUrl = TcosUtil.uploadFile(COS_AAVATAR_PATH, avatarFilename, albumAvatar.getInputStream());
        }

        Album album = new Album();
        album.setAlbumname(albumname);
        album.setCreatorId(creator);
        album.setAlbumAvatar(avatarUrl);
        album.setPublishedTime(publishedTime);
        albumService.addAlbum(album);
    }

    @GetMapping
    public PageBean<Album> searchAlbum(Integer pageNum,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String albumname,
                                       @RequestParam(required = false) Boolean mode) {
        return albumService.searchAlbum(pageNum, Objects.requireNonNullElse(pageSize, 20), albumname, Objects.requireNonNullElse(mode, false));
    }

    @PutMapping
    public void updateAlbumInfo(@RequestBody AlbumReq albumReq) {
        if(albumService.findByAid(albumReq.getAid()) == null) throw new UniverCustomException(404, "无该专辑");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin")  && !loginUser.getPermission().equals("creator")) throw new UniverCustomException(500, "普通用户没有权限修改专辑");
        if(loginUser.getPermission().equals("creator")) {
            Album editingAlbum = albumService.findByAid(albumReq.getAid());
            Integer cid = creatorService.ifExistFindByUid(uid).getCid();
            JSONArray creatorIds = JSONArray.parseArray(editingAlbum.getCreatorId());
            System.out.println(creatorIds + "   " + cid);
            if(!creatorIds.contains(cid)) throw new UniverCustomException(500, "非本人专辑无法修改");
        }
        if(loginUser.getIsBanned()) throw new UniverCustomException(500, "用户被封禁不能修改专辑");
        Album album = new Album();
        album.setAid(albumReq.getAid());
        album.setAlbumname(albumReq.getAlbumname());
        album.setCreatorId(albumReq.getCreatorId());
        album.setIntroduction(albumReq.getIntroduction());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochSecond(Long.parseLong(albumReq.getPublishedTime()));
        String publishedTimestamp = formatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        Timestamp publishedTimetsp = Timestamp.valueOf(publishedTimestamp);
        System.out.println(publishedTimestamp);
        album.setPublishedTime(publishedTimetsp);
        albumService.updateAlbumInfo(album);
    }

    @PutMapping("/avatar")
    public String updateAlbumAvatar(@RequestParam("aid") Integer aid,@RequestParam("file") MultipartFile file) throws IOException {
        if(albumService.findByAid(aid) == null) throw new UniverCustomException(404, "无该专辑");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin")  && !loginUser.getPermission().equals("creator")) throw new UniverCustomException(500, "普通用户没有权限修改音乐");
        if(loginUser.getPermission().equals("creator")) {
            Album editingAlbum = albumService.findByAid(aid);
            Integer cid = creatorService.ifExistFindByUid(uid).getCid();
            JSONArray creatorIds = JSONArray.parseArray(editingAlbum.getCreatorId());
            System.out.println(creatorIds + "   " + cid);
            if(!creatorIds.contains(cid)) throw new UniverCustomException(500, "非本人专辑无法修改");
        }
        if(loginUser.getIsBanned() == true) throw new UniverCustomException(500, "用户被封禁不能修改专辑");
        Album album = albumService.findByAid(aid);
        String oldUrl = album.getAlbumAvatar();
        TcosUtil.removeObject(oldUrl);

        String resOriginalFilename = file.getOriginalFilename();
        String resFilename = null;
        if (resOriginalFilename != null) {
            resFilename = resOriginalFilename.substring(0, resOriginalFilename.lastIndexOf(".")) + "_" + UUID.randomUUID().toString() + resOriginalFilename.substring(resOriginalFilename.lastIndexOf("."));
        }
        String resUrl = TcosUtil.uploadFile(COS_AAVATAR_PATH, resFilename, file.getInputStream());
        albumService.updateAlbumAvatar(aid, resUrl);
        return resUrl;
    }

    @DeleteMapping
    public void deleteAlbum(@RequestParam Integer aid) throws UnsupportedEncodingException {
        if(albumService.findByAid(aid) == null) throw new UniverCustomException(404, "无该专辑");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin")  && !loginUser.getPermission().equals("creator")) throw new UniverCustomException(500, "普通用户没有权限修改音乐");
        if(loginUser.getPermission().equals("creator")) {
            Album editingAlbum = albumService.findByAid(aid);
            Integer cid = creatorService.ifExistFindByUid(uid).getCid();
            JSONArray creatorIds = JSONArray.parseArray(editingAlbum.getCreatorId());
            System.out.println(creatorIds + "   " + cid);
            if(!creatorIds.contains(cid)) throw new UniverCustomException(500, "非本人专辑无法修改");
        }
        if(loginUser.getIsBanned() == true) throw new UniverCustomException(500, "用户被封禁不能修改专辑");

        Album album = albumService.findByAid(aid);
        String oldUrl = album.getAlbumAvatar();
        TcosUtil.removeObject(oldUrl);
        albumService.deleteAlbum(aid);

    }
}
