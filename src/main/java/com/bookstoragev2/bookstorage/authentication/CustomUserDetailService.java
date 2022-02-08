package com.bookstoragev2.bookstorage.authentication;

import com.bookstoragev2.bookstorage.UserRepository;
import com.bookstoragev2.bookstorage.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user =
                userRepository.findByEmail(username)
                        .orElseGet(() -> userRepository.findByUserId(username)
                                .orElseThrow(() -> new UsernameNotFoundException("")));

        return new UserAdapter(user);
    }

    public UserDetails loadUserByJwtTokenClaim(String uuid) {
        User user =
                userRepository.findById(UUID.fromString(uuid)).orElseThrow();
        return new UserAdapter(user);
    }

}
