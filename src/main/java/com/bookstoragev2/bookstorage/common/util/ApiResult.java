package com.bookstoragev2.bookstorage.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@Setter

public class ApiResult<T> {

    private final boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer size;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElement;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public ApiResult(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public ApiResult(boolean success, T result, int size, long totalElement) {
        this.success = success;
        this.result = result;
        this.size = size;
        this.totalElement = totalElement;
    }
}
