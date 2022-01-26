package me.devksh930.bookstorage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ROLE_NOT_CERTIFIED,
    ROLE_USER,
    ROLE_ADMIN
}
