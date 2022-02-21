package com.bookstoragev2.bookstorage.bookstorage;

import com.bookstoragev2.bookstorage.domain.BookStorage;
import com.bookstoragev2.bookstorage.domain.QBookStorage;
import com.bookstoragev2.bookstorage.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Slf4j
public class BookStorageRepositoryExtensionImpl extends QuerydslRepositorySupport implements BookStorageRepositoryExtension {
    public BookStorageRepositoryExtensionImpl() {
        super(BookStorage.class);
    }

    @Override
    public boolean existsByUserAndBook(User user, String isbn) {
        QBookStorage bookStorage = QBookStorage.bookStorage;
        BookStorage bookStorageCount = from(bookStorage)
                .where(bookStorage.book.isbn.contains(isbn))
                .where(bookStorage.user.eq(user))
                .fetchFirst();
        return bookStorageCount != null ? true : false;
    }
}
