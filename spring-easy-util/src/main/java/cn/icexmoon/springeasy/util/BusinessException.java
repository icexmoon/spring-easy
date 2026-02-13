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
    @Getter
    private final String code;
    @Getter
    private final Integer httpStatusCode;

    private BusinessException(Throwable cause, String message, Object data,
                             String code, Integer httpStatusCode) {
        super(message, cause);
        this.data = data;
        this.code = code;
        this.httpStatusCode = httpStatusCode;
    }

    public static BusinessExceptionBuilder builder(){
        return new BusinessExceptionBuilder();
    }

    @Data
    public static class BusinessExceptionBuilder {
        private Object data = null;
        private Throwable cause = null;
        private String message = null;
        private String code = null;
        private Integer httpStatusCode = null;

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

        public BusinessExceptionBuilder code(@Nullable String code) {
            this.code = code;
            return this;
        }

        public BusinessExceptionBuilder httpStatusCode(@Nullable Integer httpStatusCode){
            this.httpStatusCode = httpStatusCode;
            return this;
        }
        public BusinessException build() {
            return new BusinessException(cause, message, data, code, httpStatusCode);
        }
    }
}
