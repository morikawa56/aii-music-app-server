package xyz.mrkwcode.aiimusicserver.handlers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.Result;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e, HttpServletResponse response) {
        log.error("error-s");
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败", response.getStatus());
    }
    @ExceptionHandler(UniverCustomException.class)
    public Result UniverCustomExceptionHandler(UniverCustomException e, HttpServletResponse response) {
        log.error("error-ucerror");
        e.printStackTrace();
        response.setStatus(e.getCode());
        return Result.error(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(SQLException.class)
    public Result sqlError(SQLException e, HttpServletResponse response) {
        response.setStatus(500);
        e.printStackTrace();
        // 我们交给异常处理类去处理
        return Result.error(e.getMessage(), 500);
    }
}
