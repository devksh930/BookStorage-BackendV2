package me.devksh930.bookstorage.authentication;

import lombok.extern.slf4j.Slf4j;
import me.devksh930.bookstorage.TokenRepository;
import me.devksh930.bookstorage.UserRepository;
import me.devksh930.bookstorage.domain.Token;
import me.devksh930.bookstorage.user.dto.TokenDto;
import me.devksh930.bookstorage.user.dto.UserLoginDto;
import me.devksh930.bookstorage.util.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Slf4j
public class AuthService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public AuthService(@Lazy AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
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
        UserDetails userDetails = this.loadUserByJwtTokenClaim(userId);
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

    private Token generateRefreshToken(Authentication authentication) {
        Token token = new Token();
        token.setId(authentication.getName());
        token.setRefreshToken(jwtTokenProvider.createRefreshToken(authentication.getName()));
        token.setRefreshTokenTTL(jwtTokenProvider.acceesTokenExpDate());
        Token save = tokenRepository.save(token);
        return save;
    }

    public UserDetails loadUserByJwtTokenClaim(String uuid) throws UsernameNotFoundException {
        me.devksh930.bookstorage.domain.User user =
                userRepository.findById(UUID.fromString(uuid)).orElseThrow();
        return new UserAdapter(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        me.devksh930.bookstorage.domain.User user =
                userRepository.findByEmail(username)
                        .orElseGet(() -> userRepository.findById(UUID.fromString(username))
                                .orElseThrow());

        return new UserAdapter(user);
    }

}
