package com.bookstoragev2.bookstorage.config;

import com.bookstoragev2.bookstorage.authentication.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class WithSecurityContextFactory implements org.springframework.security.test.context.support.WithSecurityContextFactory<WithUser> {
    private final CustomUserDetailService userDetailService;

    @Override
    public SecurityContext createSecurityContext(WithUser withUser) {
        String email = withUser.value();
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }
}
