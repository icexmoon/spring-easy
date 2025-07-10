package cn.icexmoon.springeasy.util;

import lombok.Data;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * @ClassName BusinessException
 * @Description
 * @Author icexmoon@qq.com
 * @Date 2025/7/10 上午8:50
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {
    @Getter
    private final Object data;

    private BusinessException(@Nullable Throwable cause, @Nullable String message, @Nullable Object data) {
        super(message, cause);
        this.data = data;
    }

    public static BusinessExceptionBuilder builder(){
        return new BusinessExceptionBuilder();
    }

    @Data
    public static class BusinessExceptionBuilder {
        private Object data = null;
        private Throwable cause = null;
        private String message = null;

        public BusinessExceptionBuilder data(@Nullable Object data) {
            this.data = data;
            return this;
        }

        public BusinessExceptionBuilder cause(@Nullable Throwable cause) {
            this.cause = cause;
            return this;
        }

        public BusinessExceptionBuilder message(@Nullable String message) {
            this.message = message;
            return this;
        }
        public BusinessException build() {
            return new BusinessException(cause, message, data);
        }
    }
}
