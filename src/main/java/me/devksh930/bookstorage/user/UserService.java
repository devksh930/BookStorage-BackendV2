package me.devksh930.bookstorage.user;

import lombok.RequiredArgsConstructor;
import me.devksh930.bookstorage.UserRepository;
import me.devksh930.bookstorage.domain.RoleType;
import me.devksh930.bookstorage.domain.User;
import me.devksh930.bookstorage.user.dto.UserRequestDto;
import me.devksh930.bookstorage.user.dto.UserSignUpDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserRequestDto joinUser(UserSignUpDto userSignUpDto) {

        if (validSignUp(userSignUpDto)) {
            User user = new User(userSignUpDto.getUserId(),
                    userSignUpDto.getEmail(),
                    userSignUpDto.getUsername(),
                    userSignUpDto.getNickname(),
                    passwordEncoder.encode(userSignUpDto.getPassword()),
                    RoleType.ROLE_NOT_CERTIFIED,
                    false);

            User save = userRepository.save(user);
            return modelMapper.map(save, UserRequestDto.class);
        } else {
            throw new RuntimeException("이미존재함");
        }
    }

    public boolean validEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean validNickName(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean validUserId(String userid) {
        return userRepository.existsByUserId(userid);
    }

    private boolean validSignUp(UserSignUpDto userJoinDto) {
        return !validEmail(userJoinDto.getEmail()) &&
                !validUserId(userJoinDto.getUserId()) &&
                !validNickName(userJoinDto.getNickname());
    }

}
