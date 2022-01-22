package me.devksh930.bookstorage;

import me.devksh930.bookstorage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickName);

    boolean existsByUserId(String userId);
}
