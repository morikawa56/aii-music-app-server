package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Creator {
    private Integer cid;
    private String creatorname;
    private String gender;
    private String creatorAvator;
    private Timestamp birth;
    private String location;
    private String introduction;
}
