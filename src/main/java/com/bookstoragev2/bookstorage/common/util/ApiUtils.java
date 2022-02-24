package com.bookstoragev2.bookstorage.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiUtils {
    public static <T> ApiResult<T> success(T result) {
        return new ApiResult<>(true, result);
    }

    public static <T> ApiResult<T> successPaging(T result, int size, int currentPage, long totalElement) {
        return new ApiResult<>(true, result, size, currentPage, totalElement);
    }
}
