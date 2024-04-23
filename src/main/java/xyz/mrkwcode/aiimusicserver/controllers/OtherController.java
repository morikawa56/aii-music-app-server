package xyz.mrkwcode.aiimusicserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.User;
import xyz.mrkwcode.aiimusicserver.pojos.UserTask;
import xyz.mrkwcode.aiimusicserver.services.OtherService;
import xyz.mrkwcode.aiimusicserver.services.UserService;
import xyz.mrkwcode.aiimusicserver.utils.ThreadLocalUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/univer")
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
}
