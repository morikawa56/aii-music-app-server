package xyz.mrkwcode.aiimusicserver.annos;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import xyz.mrkwcode.aiimusicserver.validations.PermissionValidation;


import java.lang.annotation.*;

@Documented // 元注解
@Target({ElementType.FIELD}) // 元注解
@Retention(RetentionPolicy.RUNTIME) // 元注解
@Constraint(
        validatedBy = { PermissionValidation.class }
)
public @interface Permission {
    String message() default "请输入有效的权限组信息";
    // 制定分组
    Class<?>[] groups() default {};
    // 负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
