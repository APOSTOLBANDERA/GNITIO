package com.example.gnitio.controller;

import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.exception.UserAlreadyExistException;
import com.example.gnitio.exception.UserNotFoundException;
import com.example.gnitio.repository.UserRepo;
import com.example.gnitio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getOneUser(@PathVariable Long id){
        try {
            UserEntity user = userService.getOne(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.delete(id));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("ERROR");
        }
    }





}
