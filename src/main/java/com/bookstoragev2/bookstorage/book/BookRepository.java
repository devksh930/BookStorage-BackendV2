package com.bookstoragev2.bookstorage.book;

import com.bookstoragev2.bookstorage.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.isbn LIKE %:isbn%")
    Optional<Book> findByIsbnLike(@Param("isbn") String isbn);
}
