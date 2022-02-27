package com.bookstoragev2.bookstorage.bookpost;

import com.bookstoragev2.bookstorage.domain.BookPost;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface BookPostRepository extends JpaRepository<BookPost, Long>, BookPostRepositoryExtension {

    @EntityGraph(attributePaths = {"bookStorage.user"})
    Optional<BookPost> findById(Long id);
}
