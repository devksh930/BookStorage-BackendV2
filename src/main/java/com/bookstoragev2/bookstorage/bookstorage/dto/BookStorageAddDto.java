package com.bookstoragev2.bookstorage.bookstorage.dto;

import com.bookstoragev2.bookstorage.domain.BookReadType;
import lombok.Data;

@Data
public class BookStorageAddDto {
    private String isbn;
    private BookReadType readType;
}
