package com.bookstoragev2.bookstorage.bookstorage;

import com.bookstoragev2.bookstorage.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BookStorageRepositoryExtension {
    boolean existsByUserAndBook(User user, String isbn);
}
