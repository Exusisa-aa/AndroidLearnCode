package com.restaurant.common;

import lombok.Data;
import java.io.Serializable;

/**
 * Common Result Response
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // 1: Success, 0: Failure
    private String msg;
    private T data;

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 1;
        result.msg = "Success";
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
