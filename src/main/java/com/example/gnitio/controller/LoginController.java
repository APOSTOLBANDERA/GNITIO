package com.example.gnitio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}
