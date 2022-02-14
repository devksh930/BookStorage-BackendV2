package com.bookstoragev2.bookstorage.book;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<BookItemDto> items;
}
