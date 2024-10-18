package com.example.gnitio.controller;

import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.dto.LoginUserDto;
import com.example.gnitio.dto.RegistrationUserDto;

import com.example.gnitio.service.AuthenticationService;
import com.example.gnitio.service.JwtService;
import com.example.gnitio.util.JwtTokenUtils;
import com.example.gnitio.util.LoginResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/auth")

public class AuthController {

    private JwtService jwtService;
    private JwtTokenUtils jwtTokenUtils;

    private AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(JwtTokenUtils jwtTokenUtils, AuthenticationService authenticationService){
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> entering(@RequestBody LoginUserDto loginUserDto) {

        this.logger.info("Mapping login start work");
        try {
            // Аутентификация пользователя
            UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);
            this.logger.info("After authentication");
            // Генерация JWT токена
            String jwtToken = jwtTokenUtils.generateToken(authenticatedUser);

            // Создание ответа с токеном
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtTokenUtils.getExpirationTime());
            return ResponseEntity.ok(loginResponse);

        }catch (BadCredentialsException ex) {
            this.logger.error("Authentication failed: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }catch (Exception ex) {
            this.logger.error("An error occurred: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, please try again later");
        }



    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegistrationUserDto registerUserDto) {
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserEntity registeredUser = authenticationService.signup(registerUserDto);
        this.logger.info("Mapping login start work");
        return ResponseEntity.ok(registeredUser);
    }


}
