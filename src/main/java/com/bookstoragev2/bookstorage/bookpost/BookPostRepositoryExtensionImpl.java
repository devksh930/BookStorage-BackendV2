package com.bookstoragev2.bookstorage.bookpost;

import com.bookstoragev2.bookstorage.bookpost.dto.BookPostResponseDto;
import com.bookstoragev2.bookstorage.domain.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class BookPostRepositoryExtensionImpl extends QuerydslRepositorySupport implements BookPostRepositoryExtension {
    public BookPostRepositoryExtensionImpl() {
        super(BookPost.class);
    }

    @Override
    public Page<BookPostResponseDto> getBookPostWithTypeList(BookPostType bookPostType, Pageable pageable) {
        QBookPost bookPost = QBookPost.bookPost;
        JPQLQuery<BookPostResponseDto> query =
                from(bookPost)
                        .select(Projections.constructor(BookPostResponseDto.class,
                                bookPost.id,
                                bookPost.title,
                                bookPost.bookStorage.user.nickname,
                                bookPost.description,
                                bookPost.count,
                                bookPost.bookPostType,
                                bookPost.createdDate,
                                bookPost.modifiedDate))
                        .where(bookPost.bookPostType.eq(bookPostType))
                        .leftJoin(bookPost.bookStorage, QBookStorage.bookStorage)
                        .leftJoin(bookPost.bookStorage.user, QUser.user);

        long count = from(bookPost)
                .select(bookPost.id)
                .where(bookPost.bookPostType.eq(bookPostType)).fetchCount();

        JPQLQuery<BookPostResponseDto> pageableQuery = querydsl().applyPagination(pageable, query);
        List<BookPostResponseDto> items = pageableQuery.fetch();
        return new PageImpl<>(items, pageable, count);
    }

    @Override
    public Page<BookPostResponseDto> getBookPostWithUserList(User user, Pageable pageable) {
        QBookPost bookPost = QBookPost.bookPost;
        JPQLQuery<BookPostResponseDto> query =
                from(bookPost)
                        .select(Projections.constructor(BookPostResponseDto.class,
                                bookPost.id,
                                bookPost.title,
                                bookPost.bookStorage.user.nickname,
                                bookPost.description,
                                bookPost.count,
                                bookPost.bookPostType,
                                bookPost.createdDate,
                                bookPost.modifiedDate))
                        .where(bookPost.bookStorage.user.eq(user))
                        .leftJoin(bookPost.bookStorage, QBookStorage.bookStorage)
                        .leftJoin(bookPost.bookStorage.user, QUser.user);

        long count = from(bookPost)
                .select(bookPost.id)
                .where(bookPost.bookStorage.user.eq(user)).fetchCount();


        JPQLQuery<BookPostResponseDto> pageableQuery = querydsl().applyPagination(pageable, query);
        List<BookPostResponseDto> items = pageableQuery.fetch();
        return new PageImpl<>(items, pageable, count);
    }

    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}
