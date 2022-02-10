package com.bookstoragev2.bookstorage.user;

import com.bookstoragev2.bookstorage.domain.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, UUID> {
    Optional<Token> findById(UUID uuid);
}
