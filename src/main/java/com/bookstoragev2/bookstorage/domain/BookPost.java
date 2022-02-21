package com.bookstoragev2.bookstorage.domain;

import com.bookstoragev2.bookstorage.bookpost.BookPostAddDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class BookPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookPostType bookPostType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private int likeCount = 0;

    private boolean isPostPrivate = false;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookstorage_id")
    private BookStorage bookStorage;

    public boolean isBookPostOwner(User user) {
        return this.getBookStorage().getUser().equals(user);
    }

    @Builder
    public BookPost(String title, BookPostType bookPostType, String content, int likeCount, boolean isReportPrivate, boolean isDeleted, BookStorage bookStorage) {
        this.title = title;
        this.bookPostType = bookPostType;
        this.content = content;
        this.likeCount = 0;
        this.isPostPrivate = isReportPrivate;
        this.isDeleted = false;
        this.bookStorage = bookStorage;
    }

    public void bookPostModified(BookPostAddDto bookPostAddDto) {
        this.title = bookPostAddDto.getTitle();
        this.bookPostType = bookPostAddDto.getBookPostType();
        this.content = bookPostAddDto.getContent();
    }
}
