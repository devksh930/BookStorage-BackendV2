package com.bookstoragev2.bookstorage.config;

import com.bookstoragev2.bookstorage.authentication.CustomUserDetailService;
import com.bookstoragev2.bookstorage.user.UserService;
import com.bookstoragev2.bookstorage.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class WithSecurityContextFactory implements org.springframework.security.test.context.support.WithSecurityContextFactory<WithUser> {
    private final UserService userService;
    private final CustomUserDetailService userDetailService;

    @Override
    public SecurityContext createSecurityContext(WithUser withUser) {
        String email = withUser.value();

        UserSignUpDto signUpDto = new UserSignUpDto();
        signUpDto.setUserId("userid");
        signUpDto.setEmail(email);
        signUpDto.setPassword("1234");
        signUpDto.setNickname("test");
        userService.joinUser(signUpDto);

        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }
}
