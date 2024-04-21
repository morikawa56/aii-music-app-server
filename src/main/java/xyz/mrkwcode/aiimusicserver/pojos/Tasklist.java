package xyz.mrkwcode.aiimusicserver.pojos;

import lombok.Data;

@Data
public class Tasklist {
    private Integer taskId;
    private String permission;
    private Integer operator;
    private Integer beOperator;
    private String detail;
}
