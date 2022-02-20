package com.bookstoragev2.bookstorage.authentication;

import com.bookstoragev2.bookstorage.common.util.CookieUtil;
import com.bookstoragev2.bookstorage.domain.Token;
import com.bookstoragev2.bookstorage.domain.TokenType;
import com.bookstoragev2.bookstorage.user.TokenRepository;
import com.bookstoragev2.bookstorage.user.dto.TokenDto;
import com.bookstoragev2.bookstorage.user.dto.UserLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final CustomUserDetailService userDetailService;

    public AuthService(@Lazy AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository, CustomUserDetailService userDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenRepository = tokenRepository;
        this.userDetailService = userDetailService;
    }

    public TokenDto generateToken(UserLoginDto loginDto) {

        Authentication authentication = login(loginDto);
        Token token = generateRefreshToken(authentication);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setRefreshToken(token.getRefreshToken());
        tokenDto.setUserUUID(token.getId());
        tokenDto.setAccessToken(jwtTokenProvider.createAccessToken(authentication));

        return tokenDto;
    }

    public Authentication tokenLogin(String jwt) {
        String userId = jwtTokenProvider.getuserId(jwt);
        UserDetails userDetails = userDetailService.loadUserByJwtTokenClaim(userId);
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
    }

    private Authentication login(UserLoginDto loginDto) {
        return authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()));

    }


    public void logout(HttpServletRequest request, HttpServletResponse response, UUID id) {
        CookieUtil.deleteCookie(request,response,TokenType.ACCESS_TOKEN.name());
        CookieUtil.deleteCookie(request,response,TokenType.REFRESH_TOKEN.name());
        tokenRepository.deleteById(id);
    }

    private Token generateRefreshToken(Authentication authentication) {
        Token token = new Token();
        token.setId(authentication.getName());
        token.setRefreshToken(jwtTokenProvider.createRefreshToken(authentication.getName()));
        token.setRefreshTokenTTL(jwtTokenProvider.accessTokenExpDate());
        Token save = tokenRepository.save(token);
        return save;
    }

}
