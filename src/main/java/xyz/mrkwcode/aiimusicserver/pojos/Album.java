package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Album {
    private Integer aid;
    private String albumname;
    private String creatorId;
    private String Introduction;
    private String albumAvatar;
    private Timestamp publishedTime;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
