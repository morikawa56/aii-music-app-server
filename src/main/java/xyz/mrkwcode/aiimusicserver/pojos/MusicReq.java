package xyz.mrkwcode.aiimusicserver.pojos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;

@Data
public class MusicReq {
    private Integer mid;
    @NotEmpty(groups = MusicReq.Add.class)
    private String musicname;
    @NotEmpty(groups = MusicReq.Add.class)
    private String creator;
    @NotEmpty(groups = MusicReq.Add.class)
    private String album;
    private String introduction;
    private String musicAvatar;
    private Boolean isUnshelved;
    private String lyric;
    @NotEmpty(groups = MusicReq.Add.class)
    private String publishedTime;
    @URL(groups = MusicReq.Update.class)
    @NotEmpty(groups = MusicReq.Update.class)
    private String resUrl;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public interface Add extends Default {

    }

    public interface Update extends Default {

    }

    public interface UpdatePermission extends Default {

    }
}
