package com.bookstoragev2.bookstorage.controller;

import com.bookstoragev2.bookstorage.authentication.AuthService;
import com.bookstoragev2.bookstorage.authentication.CurrentUser;
import com.bookstoragev2.bookstorage.common.util.ApiResult;
import com.bookstoragev2.bookstorage.common.util.ApiUtils;
import com.bookstoragev2.bookstorage.common.util.CookieUtil;
import com.bookstoragev2.bookstorage.domain.TokenType;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.user.dto.TokenDto;
import com.bookstoragev2.bookstorage.user.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final int accessTokenTTL;
    private final int refreshTokenTTL;

    public AuthController(AuthService authService, @Value("${jwt.token.accessTokenTTL}") int accessTokenTTL, @Value("${jwt.token.refreshTokenTTL}") int refreshTokenTTL) {
        this.authService = authService;
        this.accessTokenTTL = accessTokenTTL;
        this.refreshTokenTTL = refreshTokenTTL;
    }


    @PostMapping
    public ApiResult login(@RequestBody UserLoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = authService.generateToken(loginDto);
        CookieUtil.addCookie(response, TokenType.ACCESS_TOKEN.name(), tokenDto.getAccessToken(), accessTokenTTL);
        CookieUtil.addCookie(response, TokenType.REFRESH_TOKEN.name(), tokenDto.getRefreshToken(), refreshTokenTTL);
        return ApiUtils.success(null);
    }

    @DeleteMapping
    public ApiResult logout(@CurrentUser User user, HttpServletResponse response, HttpServletRequest request) {
        authService.logout(request, response, user.getId());
        return ApiUtils.success(null);
    }

}
