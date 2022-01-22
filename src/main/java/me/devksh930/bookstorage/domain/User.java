package me.devksh930.bookstorage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, unique = true)
    @NotNull
    private String userId;

    @Column(length = 512, unique = true)
    @NotNull
    private String email;

    @Column(length = 100)
    @NotNull
    private String username;

    @Column(length = 100,unique = true)
    @NotNull
    private String nickname;

    @Column(length = 128)
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private boolean emailVerified = false;

    @Column(length = 512, unique = true)
    private String profileImageUrl = "DEFAULT_IMAGE";


}
