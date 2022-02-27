package com.bookstoragev2.bookstorage.domain;

import com.bookstoragev2.bookstorage.bookpost.dto.BookPostAddDto;
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

    @Lob
    private String content;

    private String description;

    private int likeCount = 0;

    private boolean isPostPrivate = false;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookstorage_id")
    private BookStorage bookStorage;

    private int count = 0;

    public void postCount() {
        this.count += 1;
    }

    public boolean isBookPostOwner(User user) {
        return this.getBookStorage().getUser().equals(user);
    }

    @Builder
    public BookPost(String title, BookPostType bookPostType, String content, int likeCount, boolean isReportPrivate, boolean isDeleted, BookStorage bookStorage) {
        this.title = title;
        this.bookPostType = bookPostType;
        this.content = content;
        this.description = content.length() >= 100 ?
                content.substring(0, 100).replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "").replace(System.getProperty("line.separator"), "")
                : content.replace(System.getProperty("line.separator"), "");
        this.likeCount = 0;
        this.isPostPrivate = isReportPrivate;
        this.isDeleted = false;
        this.bookStorage = bookStorage;
    }

    public void bookPostModified(BookPostAddDto bookPostAddDto) {
        this.title = bookPostAddDto.getTitle();
        this.content = bookPostAddDto.getContent();
        this.description = bookPostAddDto.getContent().length() >= 100 ?
                bookPostAddDto.getContent().substring(0, 100).replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "").replace(System.getProperty("line.separator"), "")
                : bookPostAddDto.getContent().replace(System.getProperty("line.separator"), "");
    }
}
