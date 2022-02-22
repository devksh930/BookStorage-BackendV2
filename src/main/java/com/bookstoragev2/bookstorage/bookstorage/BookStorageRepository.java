package com.bookstoragev2.bookstorage.bookstorage;

import com.bookstoragev2.bookstorage.domain.BookStorage;
import com.bookstoragev2.bookstorage.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface BookStorageRepository extends JpaRepository<BookStorage, Long>, BookStorageRepositoryExtension {
    @EntityGraph(value = "BookStorageWithBook")
    List<BookStorage> findByUser(User user);

    @EntityGraph(value = "BookStorageWithUser")
    Optional<BookStorage> findById(Long id);

    @EntityGraph(value = "BookStorageWithUser")
    Optional<BookStorage> findByIdAndUser(Long id, User user);

}
