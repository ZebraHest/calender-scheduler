package com.malteneve.userservice.controller;

import com.malteneve.userservice.config.UserAuthProvider;
import com.malteneve.userservice.domain.User;
import com.malteneve.userservice.dto.CredentialsDto;
import com.malteneve.userservice.dto.SignUpDto;
import com.malteneve.userservice.dto.UserDto;
import com.malteneve.userservice.repository.UserRepository;
import com.malteneve.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserServiceController {

    private final UserService userService;
    private final UserAuthProvider authProvider;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/validatetoken")
    public ResponseEntity<UserDto> validate(@RequestParam(name = "token") String token) {
        System.out.println("VALIDATE ATTEMPT");
        UserDto user = (UserDto) authProvider.validateToken(token).getPrincipal();
        user.setToken(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        System.out.println("LOGIN ATTEMPT");
        UserDto user = userService.login(credentialsDto);
        user.setToken(authProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        System.out.println("REGISTER ATTEMPT");
        UserDto user = userService.register(signUpDto);
        user.setToken(authProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }


}
