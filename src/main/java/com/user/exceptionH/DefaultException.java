package com.user.exceptionH;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class DefaultException {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;

    public DefaultException(String message, HttpStatus httpStatus, LocalDateTime localDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
