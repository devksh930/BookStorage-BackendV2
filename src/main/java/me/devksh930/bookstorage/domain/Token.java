package me.devksh930.bookstorage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RedisHash("Token")
public class Token {
    @Id
    private String id;

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private long refreshTokenTTL;
}
