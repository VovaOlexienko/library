package com.github.vovaolexienko.library.service;

import com.github.vovaolexienko.library.dto.CreateUserDto;
import com.github.vovaolexienko.library.entity.User;
import com.github.vovaolexienko.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void save(CreateUserDto createUserDto) {
        userRepository.save(User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getMail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .role("ROLE_USER")
                .build()
        );
    }
}
