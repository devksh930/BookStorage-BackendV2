package com.bookstoragev2.bookstorage.authentication;

import com.bookstoragev2.bookstorage.common.util.CookieUtil;
import com.bookstoragev2.bookstorage.domain.Token;
import com.bookstoragev2.bookstorage.domain.TokenType;
import com.bookstoragev2.bookstorage.user.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean isValidationAccessToken = false;
        boolean isValidationRefreshToken = false;

        final Cookie accessTokenCookie = CookieUtil.getCookie(request, TokenType.ACCESS_TOKEN.name()).orElse(null);
        final Cookie refreshTokenCookie = CookieUtil.getCookie(request, TokenType.REFRESH_TOKEN.name()).orElse(null);

        String accessToken = null;
        String refreshToken = null;

        if (accessTokenCookie != null) {
            accessToken = getCookieValue(accessTokenCookie);
            isValidationAccessToken = isTokenValid(accessToken);
        }

        if (refreshTokenCookie != null) {
            refreshToken = getCookieValue(refreshTokenCookie);
            isValidationRefreshToken = isTokenValid(refreshToken) && existsByRefreshToken(refreshToken);

        }

        log.info("????????? ?????? ????????? ?????? ??????({})", isValidationAccessToken);
        log.info("???????????? ?????? ????????? ?????? ??????({})", isValidationRefreshToken);

//        ????????? ????????? ???????????? ????????? ?????? ??????????????? ??????????????????
        if (isValidationAccessToken && isValidationRefreshToken) {
            setAuthentication(accessToken, response);
        }
//        ????????????????????? ???????????? ?????????????????? ???????????? ????????? ??????
        if (!isValidationAccessToken && isValidationRefreshToken) {
            setAuthentication(refreshToken, response);
        }
//        ????????????????????? ???????????? ????????? ?????? ?????? ???????????? ????????? ?????? ????????????.
        if (!isValidationRefreshToken) {
            CookieUtil.deleteCookie(request, response, TokenType.REFRESH_TOKEN.name());
            CookieUtil.deleteCookie(request, response, TokenType.ACCESS_TOKEN.name());
            if (accessToken != null) {
                tokenRepository.deleteById(UUID.fromString(jwtTokenProvider.getuserId(accessToken)));
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token, HttpServletResponse response) {
        Authentication authentication = authService.tokenLogin(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CookieUtil.addCookie(response, TokenType.ACCESS_TOKEN.name(), jwtTokenProvider.createAccessToken(authentication), jwtTokenProvider.accessTokenExpDate() / 1000);
    }

    private boolean isTokenValid(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    private String getCookieValue(Cookie cookie) {
        return CookieUtil.deserialize(cookie, String.class);
    }

    // ??????????????? refreshToken?????? Redis??? ???????????? refreshToken??? ????????????.
    private boolean existsByRefreshToken(String refreshToken) {
        String getuserId = jwtTokenProvider.getuserId(refreshToken);
        Token token = tokenRepository.findById(UUID.fromString(getuserId)).orElse(null);
        return token != null && token.getRefreshToken().equals(refreshToken) ? true : false;
    }


}





