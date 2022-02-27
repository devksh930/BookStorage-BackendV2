package com.bookstoragev2.bookstorage.bookpost.dto;

import com.bookstoragev2.bookstorage.domain.BookPost;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookPostResponseDto {
    private Long id;

    private String title;

    private String writer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;

    private String description;

    private int count;

    private BookPostType bookPostType;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    @Builder
    public BookPostResponseDto(BookPost bookPost) {
        this.id = bookPost.getId();
        this.title = bookPost.getTitle();
        this.writer = bookPost.getBookStorage().getUser().getNickname();
        this.content = bookPost.getContent();
        this.description = bookPost.getDescription();
        this.count = bookPost.getCount();
        this.bookPostType = bookPost.getBookPostType();
        this.createDate = bookPost.getCreatedDate();
        this.modifiedDate = bookPost.getModifiedDate();
    }

    @QueryProjection
    public BookPostResponseDto(Long id, String title, String writer, String description, int count, BookPostType bookPostType, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.count = count;
        this.bookPostType = bookPostType;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
