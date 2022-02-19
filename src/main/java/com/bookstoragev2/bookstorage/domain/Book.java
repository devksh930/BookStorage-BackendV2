package com.bookstoragev2.bookstorage.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Book extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    private String image;

    private String author;

    private String price;

    private String discount;

    private String publisher;

    private String pubdate;

    @Column(unique = true)
    private String isbn;

    private String description;

    public Book(String title, String link, String image, String author, String price, String discount, String publisher, String pubdate, String isbn, String description) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
        this.price = price;
        this.discount = discount;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.isbn = isbn;
        this.description = description;
    }
}
