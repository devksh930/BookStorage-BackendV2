package me.devksh930.bookstorage.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserSignUpDto {
    @NotNull
    private String userId;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

}
