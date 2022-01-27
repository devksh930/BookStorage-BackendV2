package me.devksh930.bookstorage.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserSignUpDto {
    @NotNull
    private String userId;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

}
