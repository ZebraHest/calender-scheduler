package com.malteneve.userservice.services;

import com.malteneve.userservice.domain.User;
import com.malteneve.userservice.dto.CredentialsDto;
import com.malteneve.userservice.dto.SignUpDto;
import com.malteneve.userservice.dto.UserDto;
import com.malteneve.userservice.exceptions.AppException;
import com.malteneve.userservice.mappers.UserMapper;
import com.malteneve.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> byLogin = userRepository.findByLogin(signUpDto.login());
        if (byLogin.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User saveUser = userRepository.save(user);

        return userMapper.toUserDto(saveUser);
    }
}
