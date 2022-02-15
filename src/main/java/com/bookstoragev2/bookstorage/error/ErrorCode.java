package com.bookstoragev2.bookstorage.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 값을 입력하셨습니다"),
    METHOD_NOT_ALLOWED(405, "C002", "지원하지 않는 메서드입니다"),
    HANDLE_ACCESS_DENIED(403, "C003", "지원하지 않는 권한입니다."),
    HANDLE_AUTHENTICATION_ENTRYPOINT(401,"C004","로그인후 사용가능합니다."),

    // User
    EMAIL_DUPLICATION(400, "U001", "잘못된 email형식 입니다."),
    LOGIN_INPUT_INVALID(400, "U002", "email또는 userID를 다시 확인하십시오"),
    USER_EXISTS_JOIN(409,"U003","이미 존재하는 계정 입니다. 입력하신 값을 한번 확인해주세요."),

    // Book
    BOOK_DETAIL_NOTFOUND(404,"B001","검색하신 책을 찾을수 없습니다.")
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
