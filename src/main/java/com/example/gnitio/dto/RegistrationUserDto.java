package com.example.gnitio.dto;

import com.example.gnitio.entity.RoleEntity;
import com.example.gnitio.entity.ToDoEntity;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

public class RegistrationUserDto {


    private String username;
    private String password;
    private String confirmPassword;
    private String email;


}
