package xyz.mrkwcode.aiimusicserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.pojos.Music;
import xyz.mrkwcode.aiimusicserver.services.MusicService;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Slf4j
@RestController
@RequestMapping("/api/music")
@Validated
@ResponseResult
public class MusicController {
    @Autowired
    private MusicService musicService;

    @PostMapping
    public void addMusic(HttpServletRequest req, // 音乐信息主干
                         @RequestParam("res") MultipartFile resource,
                         @RequestParam("musicAvatar") MultipartFile musicAvatar) {
        String musicname = req.getParameter("musicname").trim();
        String creator = req.getParameter("creator").trim();
        String album = req.getParameter("album").trim();
        String publishedTimeStr = req.getParameter("publishedTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochSecond(Long.parseLong(publishedTimeStr));
        String publishedTimestamp = formatter.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        Timestamp publishedTime = Timestamp.valueOf(publishedTimestamp);
        String introduction = req.getParameter("introduction").trim();


    }
}
