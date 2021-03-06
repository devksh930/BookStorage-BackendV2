package com.bookstoragev2.bookstorage.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User extends BaseTimeEntity {
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

    @Column(length = 100, unique = true)
    @NotNull
    private String nickname;

    @Column(length = 128)
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private boolean emailVerified;

    @Column(length = 512)
    private String profileImageUrl = "DEFAULT_IMAGE";


    public User(String userId, String email, String nickname, String password, RoleType roleType) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.roleType = roleType;
        this.emailVerified = false;
    }
}
