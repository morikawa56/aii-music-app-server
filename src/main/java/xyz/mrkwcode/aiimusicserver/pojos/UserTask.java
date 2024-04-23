package xyz.mrkwcode.aiimusicserver.pojos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mrkwcode.aiimusicserver.annos.Permission;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTask {
    private Integer taskId;
    @Permission
    private String permission;
    private Integer operator;
    private Integer beOperator;
    private String status;
    @NotEmpty
    private String detail;

    public static int compareByTaskId(UserTask userTask1, UserTask userTask2) {
        return userTask1.getTaskId().compareTo(userTask2.getTaskId());
    }
}
