package com.bookstoragev2.bookstorage.user;

import com.bookstoragev2.bookstorage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickName);

    boolean existsByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);
}
