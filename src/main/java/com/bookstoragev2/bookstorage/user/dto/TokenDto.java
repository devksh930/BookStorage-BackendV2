package com.bookstoragev2.bookstorage.user.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String accessToken;

    private String refreshToken;

    private String userUUID;

    private long refreshTokenTTL;
}
