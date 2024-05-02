package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlbumReq {
    private Integer aid;
    private String albumname;
    private String creatorId;
    private String introduction;
    private String publishedTime;
}
