package com.bookstoragev2.bookstorage.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiUtils {
    public static <T> ApiResult<T> success(T result) {
        return new ApiResult<>(true, result);
    }
}
