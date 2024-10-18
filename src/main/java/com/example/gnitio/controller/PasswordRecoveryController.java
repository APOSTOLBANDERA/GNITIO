package com.example.gnitio.controller;

import com.example.gnitio.service.PasswordRecoveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    // Шаг 1: Отправка email для восстановления
    @PostMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestParam("email") String email) {
        passwordRecoveryService.sendRecoveryCode(email);
        return ResponseEntity.ok("Recovery code sent to " + email);
    }

    // Шаг 2: Подтверждение кода
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmRecoveryCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        boolean isValid = passwordRecoveryService.verifyRecoveryCode(email, code);
        if (isValid) {
            return ResponseEntity.ok("Code confirmed");
        } else {
            return ResponseEntity.badRequest().body("Invalid recovery code");
        }
    }

    // Шаг 3: Установка нового пароля
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        passwordRecoveryService.updatePassword(email, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }
}
