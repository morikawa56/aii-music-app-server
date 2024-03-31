package xyz.mrkwcode.aiimusicserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UniverCustomException extends RuntimeException{
    private int code;
    private String msg;
}
