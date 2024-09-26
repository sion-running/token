package com.practice.token.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, "Duplicate user name"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, null),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, null),
    ;

    private final HttpStatus status;
    private final String desc;
}
