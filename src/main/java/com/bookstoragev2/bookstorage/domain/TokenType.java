package com.bookstoragev2.bookstorage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN
}
