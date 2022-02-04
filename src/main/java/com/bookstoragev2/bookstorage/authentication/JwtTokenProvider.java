package com.bookstoragev2.bookstorage.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import com.bookstoragev2.bookstorage.domain.TokenType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    private final String secret;
    private final long accessTokenTTL;
    private final long refreshTokenTTL;
    private final String tokenIssure;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.token.accessTokenTTL}") long accessTokenTTL,
                            @Value("${jwt.token.refreshTokenTTL}") long refreshTokenTTL,
                            @Value("${jwt.token.issure}") String tokenIssure) {

        this.secret = secret;
        this.accessTokenTTL = accessTokenTTL * 1000;
        this.refreshTokenTTL = refreshTokenTTL * 1000;
        this.tokenIssure = tokenIssure;
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        log.debug("accessTokeTTL : {}ms", accessTokenTTL);


        return Jwts.builder()
                .setSubject(TokenType.ACCESS_TOKEN.name())
                .signWith(key, SignatureAlgorithm.HS512)
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", authentication.getName())
                .setIssuer(tokenIssure)
                .setIssuedAt(new Date())
                .setExpiration(makeTTlTime(this.accessTokenTTL))
                .compact();
    }

    public String createRefreshToken(String uuid) {
        log.debug("refreshTokeTTL : {}ms", refreshTokenTTL);

        return Jwts.builder()
                .setSubject(TokenType.REFRESH_TOKEN.name())
                .signWith(key, SignatureAlgorithm.HS512)
                .claim("userId", uuid)
                .setIssuer(tokenIssure)
                .setExpiration(makeTTlTime(this.refreshTokenTTL))
                .setIssuedAt(new Date())
                .compact();
    }


    private Date makeTTlTime(long ttlTime) {
        return new Date((new Date()).getTime() + ttlTime);
    }

    public long accessTokenExpDate() {
        return accessTokenTTL;
    }

    public long refreshTokenExpDate() {
        return refreshTokenTTL;
    }

    public String getuserId(String jwt) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(jwt).getBody().get("userId");
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build().parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }


    //빈초기화시 key주입
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
}
