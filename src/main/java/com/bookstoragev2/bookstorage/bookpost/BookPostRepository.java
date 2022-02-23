package com.bookstoragev2.bookstorage.bookpost;

import com.bookstoragev2.bookstorage.domain.BookPost;
import com.bookstoragev2.bookstorage.domain.BookPostType;
import com.bookstoragev2.bookstorage.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface BookPostRepository extends JpaRepository<BookPost, Long> {

    @EntityGraph(attributePaths = {"bookStorage.user"})
    Optional<BookPost> findById(Long id);

    @EntityGraph(attributePaths = {"bookStorage.user"})
    Optional<BookPost> findByIdAndBookStorageUser(Long id, User user);

    @EntityGraph(attributePaths = {"bookStorage.user"})
    Page<BookPost> findByBookPostType(BookPostType postType, Pageable pageable);

    @EntityGraph(attributePaths = {"bookStorage.user"})
    Page<BookPost> findByBookStorageUser(User user, Pageable pageable);
}
