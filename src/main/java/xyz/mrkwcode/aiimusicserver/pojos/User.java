package xyz.mrkwcode.aiimusicserver.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    @NotNull
    private Integer uid;
    @NotEmpty
    private String username;
    @JsonIgnore // 让springmvc把当前对象转换成字符串是自动忽略该属性
    private String password;
    private String nickname;
    private String emailAddress;
    private String avatarUrl;
    private String permission;
    private String profile;
    private long createdTime;
    private long updatedTime;
    private Integer isBanned;
    private long bannedStart;
    private long bannedEnd;
}
