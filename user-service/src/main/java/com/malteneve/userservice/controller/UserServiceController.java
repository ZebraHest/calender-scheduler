package com.malteneve.userservice.controller;

import com.malteneve.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.malteneve.userservice.domain.User;

@RestController
public class UserServiceController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public @ResponseBody String createUser(
            @RequestParam(value = "name") String name) {

        User n = new User();
        n.setName(name);
        userRepository.save(n);
        return "test";
    }

   @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
}
