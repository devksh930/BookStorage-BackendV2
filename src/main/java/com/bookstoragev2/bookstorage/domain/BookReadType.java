package com.bookstoragev2.bookstorage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookReadType {
    READ,     //읽음
    READING,  //읽는중
    NOT_READ  // 읽지않음
}
