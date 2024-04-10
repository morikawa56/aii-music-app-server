package xyz.mrkwcode.aiimusicserver.annos;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import xyz.mrkwcode.aiimusicserver.validations.GenderValidation;

import java.lang.annotation.*;

@Documented // 元注解
@Target({ElementType.FIELD, ElementType.PARAMETER}) // 元注解
@Retention(RetentionPolicy.RUNTIME) // 元注解
@Constraint(
        validatedBy = { GenderValidation.class }
)
public @interface Gender {
    String message() default "请输入有效的性别信息";
    // 制定分组
    Class<?>[] groups() default {};
    // 负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
