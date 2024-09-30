package com.example.gnitio.controller;

import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.exception.UserAlreadyExistException;
import com.example.gnitio.exception.UserNotFoundException;
import com.example.gnitio.repository.UserRepo;
import com.example.gnitio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity registration(@RequestBody UserEntity user){
        try {
            userService.createNewUser(user);
            return ResponseEntity.ok("USER SAVED");
        }
        catch (UserAlreadyExistException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body("ERROR");
        }

    }

    @GetMapping("/")

    public ResponseEntity getOneUser(@RequestParam Long id){
        try {
            return ResponseEntity.ok(userService.getOne(id));
        }catch (UserNotFoundException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("ERROR");
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
