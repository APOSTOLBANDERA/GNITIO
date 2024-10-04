package com.example.gnitio.dto;

import com.example.gnitio.entity.RoleEntity;
import com.example.gnitio.entity.ToDoEntity;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

public class RegistrationUserDto {


    private String username;
    private String password;
    private String email;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
