package com.github.vovaolexienko.library.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class CreateUserDto {

    @Email
    private String mail;

    @Size(min = 3, max = 15)
    private String username;

    @Size(min = 8, max = 20)
    private String password;
}
