package com.bookstoragev2.bookstorage.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;


    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
