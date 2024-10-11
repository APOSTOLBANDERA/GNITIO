package com.example.gnitio.controller;

import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.dto.LoginUserDto;
import com.example.gnitio.dto.RegistrationUserDto;

import com.example.gnitio.service.AuthenticationService;
import com.example.gnitio.service.JwtService;
import com.example.gnitio.util.LoginResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/auth")

public class AuthController {

    private JwtService jwtService;

    private AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(JwtService jwtService, AuthenticationService authenticationService){
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> entering(@RequestBody LoginUserDto loginUserDto) {

        System.out.println("ajajaja");
        this.logger.info("Mapping login start work");
        try {
            UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);
            this.logger.info("After authentication");
            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            this.logger.info("Mapping login start work");

            return ResponseEntity.ok(loginResponse);
        } catch (Exception ex){
            this.logger.error(ex.getMessage());
        }
        return null;





    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegistrationUserDto registerUserDto) {
        UserEntity registeredUser = authenticationService.signup(registerUserDto);
        this.logger.info("Mapping login start work");
        return ResponseEntity.ok(registeredUser);
    }


}
