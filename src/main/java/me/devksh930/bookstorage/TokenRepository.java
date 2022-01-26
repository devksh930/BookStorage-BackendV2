package me.devksh930.bookstorage;

import me.devksh930.bookstorage.domain.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, UUID> {
    Optional<Token> findById(UUID uuid);
}