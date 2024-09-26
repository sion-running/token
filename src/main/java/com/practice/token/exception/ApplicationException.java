package com.practice.token.exception;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ApplicationException extends RuntimeException {
    private ErrorCode errorCode;
    private String desc;
    private String param;

    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.desc = errorCode.getDesc();
    }

    public ApplicationException(ErrorCode errorCode, String param) {
        this.errorCode = errorCode;
        this.desc = errorCode.getDesc();
        this.param = param;
    }

    @Override
    public String getMessage() {
        if (Objects.isNull(param)) {
            return String.format("%s. %s", errorCode.name(), desc);
        }

        return String.format("%s. caused by %s.", errorCode.name(), param);
    }
}
