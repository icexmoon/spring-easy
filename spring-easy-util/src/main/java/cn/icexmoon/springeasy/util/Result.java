package cn.icexmoon.springeasy.util;

import lombok.Getter;
import lombok.ToString;

/**
 * @ClassName Result
 * @Description Controller 标准返回的封装
 * @Author icexmoon@qq.com
 * @Date 2025/7/9 上午9:47
 * @Version 1.0
 */
@Getter
@ToString
public class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <D> Result<D> success(D data) {
        return success(data, null);
    }

    public static <D> Result<D> success(D data, String message) {
        return new Result<>(true, message, data);
    }

    public static Result<Void> success(String message) {
        return new Result<>(true, message, null);
    }

    public static Result<Void> success() {
        return success(null);
    }

    public static Result<Void> fail(){
        return fail(null);
    }

    public static Result<Void> fail(String message) {
        return fail(null, message);
    }

    public static <D> Result<D> fail(D data, String message) {
        return new Result<>(false, message, data);
    }
}
