package com.bookstoragev2.bookstorage.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserAdapter extends User {
    private com.bookstoragev2.bookstorage.domain.User user;

    public UserAdapter(com.bookstoragev2.bookstorage.domain.User user) {
        super(user.getId().toString(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoleType().name())));
        this.user = user;
    }

    public com.bookstoragev2.bookstorage.domain.User getUser() {
        return user;
    }
}
