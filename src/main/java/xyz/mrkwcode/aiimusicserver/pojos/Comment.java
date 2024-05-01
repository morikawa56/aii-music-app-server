package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private Integer cmid;
    private Integer uid;
    private Integer mid;
    private Integer mlid;
    private String content;
    private Integer up;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Boolean isMusiclist;
}
