package me.devksh930.bookstorage.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserAdapter extends User {
    private me.devksh930.bookstorage.domain.User user;

    public UserAdapter(me.devksh930.bookstorage.domain.User user) {
        super(user.getId().toString(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoleType().name())));
        this.user = user;
    }

    public me.devksh930.bookstorage.domain.User getUser() {
        return user;
    }
}
