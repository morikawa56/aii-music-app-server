package xyz.mrkwcode.aiimusicserver.pojos;


//统一响应结果

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private Integer status;
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data, Integer status) {
        return new Result<>(0, status, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success(Integer status) {
        return new Result(0, status, "操作成功", null);
    }

    public static Result error(String message, Integer status) {
        return new Result(1, status, message, null);
    }
}
