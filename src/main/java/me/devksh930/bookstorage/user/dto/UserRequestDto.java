package me.devksh930.bookstorage.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String nickname;
    private String email;
    private String profileImageUrl;
}
