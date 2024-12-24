package com.cars.management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorException extends RuntimeException {

    private final HttpStatus status;

    public ErrorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static ErrorException badRequest(String message, Object... data) {
        return new ErrorException(message, HttpStatus.BAD_REQUEST);
    }


    public static ErrorException unauthorized(String message) {
        return new ErrorException(message, HttpStatus.UNAUTHORIZED);
    }

    public static ErrorException internalError(String message, Object... data) {
        return new ErrorException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ErrorException forbidden(String message, Object... data) {
        return new ErrorException(message, HttpStatus.FORBIDDEN);
    }

    public static ErrorException resourceNotExist(String message, Object... data) {
        return new ErrorException(message, HttpStatus.NOT_FOUND);
    }

    public static ErrorException resourceExist(String message, Object... data) {
        return new ErrorException(message, HttpStatus.CONFLICT);
    }



}
