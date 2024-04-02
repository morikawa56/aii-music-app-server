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
    @NotEmpty
    private String detail;
}
