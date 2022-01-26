package me.devksh930.bookstorage.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "BINARY(16)")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 64, unique = true)
    @NotNull
    private String userId;

    @Column(length = 512, unique = true)
    @NotNull
    private String email;

    @Column(length = 100)
    @NotNull
    private String username;

    @Column(length = 100, unique = true)
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


    public User(String userId, String email, String username, String nickname, String password, RoleType roleType, boolean emailVerified) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.roleType = roleType;
        this.emailVerified = emailVerified;
    }
}
