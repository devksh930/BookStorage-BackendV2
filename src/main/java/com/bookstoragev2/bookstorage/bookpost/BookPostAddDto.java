package com.bookstoragev2.bookstorage.bookpost;

import com.bookstoragev2.bookstorage.domain.BookPostType;
import lombok.Data;

@Data
public class BookPostAddDto {
    private String title;

    private BookPostType bookPostType;

    private String content;

}
