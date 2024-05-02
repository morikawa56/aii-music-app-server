package xyz.mrkwcode.aiimusicserver.controllers;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.Comment;
import xyz.mrkwcode.aiimusicserver.pojos.Musiclist;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;
import xyz.mrkwcode.aiimusicserver.services.OtherService;
import xyz.mrkwcode.aiimusicserver.services.UserService;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;
import xyz.mrkwcode.aiimusicserver.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
@ResponseResult
public class OtherController {
    @Autowired
    private OtherService otherService;

    @Autowired
    private UserService userService;

    @GetMapping("/tasklist")
    public List<UserTask> findAllTaskList(@RequestParam(required = false) Integer uid) {
        List<UserTask> tasks = otherService.findAllTaskList(uid);
        return tasks;
    }

    @PatchMapping("/banuser")
    public void banUser(@RequestParam Integer taskid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin")) {
            throw new UniverCustomException(500, "您没有权限禁用账户");
        } else {
            UserTask userTask = otherService.findTaskByTaskId(taskid);
            otherService.banUser(userTask);
        }
    }

    @PatchMapping("/changePermission")
    public void changePermission(@RequestParam Integer taskid) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin")) {
            throw new UniverCustomException(500, "您没有权限修改用户权限");
        } else {
            UserTask userTask = otherService.findTaskByTaskId(taskid);
            otherService.changePermission(userTask);
        }
    }

    @PostMapping("/comment")
    public void addComment(@RequestParam(required = false) Integer mid,
                           @RequestParam(required = false) Integer mlid,
                           @RequestParam String content) {
        if(mid == null && mlid == null) {
            throw new UniverCustomException(500, "请至少填入一个mid或mlid");
        }
        if(mid != null && mlid != null) {
            throw new UniverCustomException(500, "mlid或mid只能填入一个");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(loginUser.getIsBanned() == true) throw new UniverCustomException(500, "用户被封禁不能进行评论");

        Boolean isMusiclist = mid == null ? true : false;
        Comment comment = new Comment();
        comment.setUid(uid);
        comment.setIsMusiclist(isMusiclist);
        if (isMusiclist == false) {
            comment.setMid(mid);
        } else {
            comment.setMlid(mlid);
        }
        comment.setContent(content.trim());
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp time = Timestamp.valueOf(now);
        comment.setCreatedTime(time);
        comment.setUpdatedTime(time);
        otherService.addComment(comment);
    }


    @DeleteMapping("/comment")
    public void removeComment(@RequestParam Integer cmid) {
        Comment comment = otherService.findCommentByCmid(cmid);
        if (comment == null) throw new UniverCustomException(500, "该评论不存在");
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("uid");
        User loginUser = userService.findByUid(uid);
        if(!loginUser.getPermission().equals("admin") || !comment.getUid().equals(uid)) throw new UniverCustomException(500, "非管理员禁止删除他人评论");
        otherService.removeComment(cmid);
    }

    @PostMapping("/comment/like")
    public void like(@RequestParam Integer cmid, @RequestParam Integer up) {
        Comment thisComment = otherService.findCommentByCmid(cmid);
        if (thisComment == null) throw new UniverCustomException(500, "该评论不存在");
        Comment comment = new Comment();
        comment.setCmid(cmid);
        comment.setUp(up);
        String now = TimeUtil.dateToString(new Date(), TimeUtil.TIME_FULL_SPRIT);
        Timestamp time = Timestamp.valueOf(now);
        comment.setUpdatedTime(time);
        otherService.like(comment);
    }

}
