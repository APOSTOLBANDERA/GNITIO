package com.example.gnitio.service;

import com.example.gnitio.dto.LoginUserDto;
import com.example.gnitio.dto.RegistrationUserDto;
import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.repository.RoleRepo;
import com.example.gnitio.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.gnitio.entity.RoleEntity;

import java.util.Collections;

@Service
public class AuthenticationService {
    private final UserRepo userRepository;

    private RoleRepo roleRepository;
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepo userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    public UserEntity signup(RegistrationUserDto input) {
        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setPassword(input.getPassword());
        user.setEmail(input.getEmail());

        String roleName = input.getRole() != null && input.getRole().equals("admin") ? "ROLE_ADMIN" : "ROLE_USER";
        RoleEntity role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));


        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
