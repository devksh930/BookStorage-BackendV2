package me.devksh930.bookstorage.controller;

import lombok.RequiredArgsConstructor;
import me.devksh930.bookstorage.domain.TokenType;
import me.devksh930.bookstorage.authentication.AuthService;
import me.devksh930.bookstorage.user.dto.TokenDto;
import me.devksh930.bookstorage.user.dto.UserLoginDto;
import me.devksh930.bookstorage.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @Value("${jwt.token.accessTokenTTL}")
    private final int accessTokenTTL;
    @Value("${jwt.token.refreshTokenTTL}")
    private final int refreshTokenTTL;

    public AuthController(AuthService authService,
                          @Value("${jwt.token.accessTokenTTL}") int accessTokenTTL,
                          @Value("${jwt.token.refreshTokenTTL}") int refreshTokenTTL) {
        this.authService = authService;
        this.accessTokenTTL = accessTokenTTL;
        this.refreshTokenTTL = refreshTokenTTL;
    }


    @PostMapping
    public TokenDto login(@RequestBody UserLoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = authService.generateToken(loginDto);
        CookieUtil.addCookie(response, TokenType.ACCESS_TOKEN.name(), tokenDto.getAccessToken(), accessTokenTTL);
        CookieUtil.addCookie(response, TokenType.REFRESH_TOKEN.name(), tokenDto.getRefreshToken(), refreshTokenTTL);
        return tokenDto;
    }

}
