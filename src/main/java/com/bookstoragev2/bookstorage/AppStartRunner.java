package com.bookstoragev2.bookstorage;

import com.bookstoragev2.bookstorage.user.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartRunner implements ApplicationRunner {
    private final TokenRepository tokenRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        tokenRepository.deleteAll();
    }
}
