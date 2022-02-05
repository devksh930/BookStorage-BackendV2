package com.bookstoragev2.bookstorage.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String nickname;
    private String email;
    private String profileImageUrl;
}
