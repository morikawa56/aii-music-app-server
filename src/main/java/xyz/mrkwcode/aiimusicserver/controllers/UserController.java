package xyz.mrkwcode.aiimusicserver.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mrkwcode.aiimusicserver.annos.ResponseResult;

@RestController
@RequestMapping("/api/user")
@Validated
@ResponseResult
public class UserController {

}
