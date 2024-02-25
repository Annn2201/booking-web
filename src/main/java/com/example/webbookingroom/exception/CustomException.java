package com.example.webbookingroom.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private HttpStatus httpStatus;
    public CustomException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}
