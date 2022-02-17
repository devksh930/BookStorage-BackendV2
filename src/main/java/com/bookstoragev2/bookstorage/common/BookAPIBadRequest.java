package com.bookstoragev2.bookstorage.common;

import com.bookstoragev2.bookstorage.error.ErrorResponse;
import lombok.Getter;

@Getter
public class BookAPIBadRequest extends RuntimeException {
    private ErrorResponse errorResponse;
    public BookAPIBadRequest(ErrorResponse errorMessage) {
        this.errorResponse = errorMessage;
    }
}
