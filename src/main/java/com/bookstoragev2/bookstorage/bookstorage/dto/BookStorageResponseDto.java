package com.bookstoragev2.bookstorage.bookstorage.dto;

import com.bookstoragev2.bookstorage.domain.BookStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookStorageResponseDto {
    private Long id;
    private String title;
    private String link;
    private String image;
    private String author;
    private String publisher;
    private String isbn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BookStorageResponseDto(BookStorage bookStorage) {
        id = bookStorage.getId();
        title = bookStorage.getBook().getTitle();
        link = bookStorage.getBook().getLink();
        image = bookStorage.getBook().getImage();
        author = bookStorage.getBook().getAuthor();
        publisher = bookStorage.getBook().getPublisher();
        isbn = bookStorage.getBook().getIsbn();
        createdDate = bookStorage.getCreatedDate();
        modifiedDate = bookStorage.getModifiedDate();
    }
}
