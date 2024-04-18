package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Musiclist {
    private Integer mlid;
    private String musiclistname;
    private Integer uid;
    private String musiclistPic;
    private String introduction;
    private String style;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
