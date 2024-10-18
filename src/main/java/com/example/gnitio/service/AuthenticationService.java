package com.example.gnitio.service;

import com.example.gnitio.controller.AuthController;
import com.example.gnitio.dto.LoginUserDto;
import com.example.gnitio.dto.RegistrationUserDto;
import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.repository.RoleRepo;
import com.example.gnitio.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.gnitio.entity.RoleEntity;

import java.util.Collections;
import java.util.NoSuchElementException;


@Service
public class AuthenticationService {
    private final UserRepo userRepository;

    private RoleRepo roleRepository;
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserEntity signup(RegistrationUserDto input) {
        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail());

        String roleName = input.getRole() != null && input.getRole().equals("admin") ? "ROLE_ADMIN" : "ROLE_USER";
        RoleEntity role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));


        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        try {
            // Логируем email для отладки
            logger.debug("Attempting to authenticate user with email: " + input.getEmail());

            // Аутентификация пользователя по email и паролю
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );

            // Поиск пользователя по email
            return userRepository.findByEmail(input.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found with email: " + input.getEmail()));
        } catch (BadCredentialsException ex) {
            logger.error("Invalid email or password", ex);
            throw new BadCredentialsException("Invalid email or password", ex);
        }
    }
}
