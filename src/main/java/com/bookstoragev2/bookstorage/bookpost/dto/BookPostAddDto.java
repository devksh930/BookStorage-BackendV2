package com.bookstoragev2.bookstorage.bookpost.dto;

import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class BookPostAddDto {
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BookPostType bookPostType;

    private String content;

}
