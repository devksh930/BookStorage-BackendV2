package com.bookstoragev2.bookstorage.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@NamedEntityGraph(name = "BookStorageWithBook",attributeNodes = @NamedAttributeNode("book"))
public class BookStorage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookReadType bookReadType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookStorage(BookReadType bookReadType, User user, Book book) {
        this.bookReadType = bookReadType;
        this.user = user;
        this.book = book;
    }
}
