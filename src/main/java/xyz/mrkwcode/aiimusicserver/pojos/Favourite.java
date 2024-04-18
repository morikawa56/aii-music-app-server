package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Favourite {
    private Integer fid;
    private Integer uid;
    private Boolean isMusiclist;
    private Integer mid;
    private Integer mlid;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
