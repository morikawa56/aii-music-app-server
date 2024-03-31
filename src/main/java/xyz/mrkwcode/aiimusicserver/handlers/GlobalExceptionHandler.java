package xyz.mrkwcode.aiimusicserver.handlers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mrkwcode.aiimusicserver.exceptions.UniverCustomException;
import xyz.mrkwcode.aiimusicserver.pojos.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e, HttpServletResponse response) {
        log.error("error-s");
        return Result.error(e.getMessage(), response.getStatus());
    }
    @ExceptionHandler(UniverCustomException.class)
    public Result UniverCustomExceptionHandler(UniverCustomException e, HttpServletResponse response) {
        log.error("error-ucerror");
        response.setStatus(e.getCode());
        return Result.error(e.getMsg(), e.getCode());
    }
}
