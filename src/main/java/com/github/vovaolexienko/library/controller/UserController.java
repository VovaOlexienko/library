package com.github.vovaolexienko.library.controller;

import com.github.vovaolexienko.library.dto.CreateUserDto;
import com.github.vovaolexienko.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userDao;

    @GetMapping(value = {"/registration"})
    public String registration() {
        return "signUp";
    }

    @GetMapping(value = {"/login"})
    public String login() {
        return "signIn";
    }

    @PostMapping(value = {"/signUp"})
    public String signUp(@Valid CreateUserDto createUserDto) {
        userDao.save(createUserDto);
        return "signIn";
    }
}