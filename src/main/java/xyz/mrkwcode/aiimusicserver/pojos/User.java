package xyz.mrkwcode.aiimusicserver.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import xyz.mrkwcode.aiimusicserver.annos.Permission;

import java.sql.Timestamp;

@Data
public class User {
    private Integer uid;
    @NotEmpty(groups = Add.class)
    private String username;
    @JsonIgnore // 让springmvc把当前对象转换成字符串是自动忽略该属性
    private String password;
    @NotEmpty(groups = Update.class)
    @Pattern(regexp = "^\\S{1,20}$")
    private String nickname;
    @Email
    private String emailAddress;
    private String phoneNumber;
    @URL
    private String avatarUrl;
    @Permission(groups = UpdatePermission.class)
    private String permission;
    @NotNull(groups = Update.class)
    private String profile;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Boolean isBanned;
    private Timestamp bannedStart;
    private Timestamp bannedEnd;

    public interface Add extends Default {

    }

    public interface Update extends Default {

    }

    public interface UpdatePermission extends Default {

    }

}
