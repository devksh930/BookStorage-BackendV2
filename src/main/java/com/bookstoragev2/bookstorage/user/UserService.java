package com.bookstoragev2.bookstorage.user;

import com.bookstoragev2.bookstorage.UserRepository;
import com.bookstoragev2.bookstorage.domain.RoleType;
import com.bookstoragev2.bookstorage.domain.User;
import com.bookstoragev2.bookstorage.error.exception.UserJoinExistException;
import com.bookstoragev2.bookstorage.user.dto.UserRequestDto;
import com.bookstoragev2.bookstorage.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
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
                    userSignUpDto.getNickname(),
                    passwordEncoder.encode(userSignUpDto.getPassword()),
                    RoleType.ROLE_NOT_CERTIFIED,
                    false);

            User save = userRepository.save(user);
            return modelMapper.map(save, UserRequestDto.class);
        } else {
            throw new UserJoinExistException();
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
