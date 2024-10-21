package com.example.gnitio.service;

import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PasswordRecoveryService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final Map<String, String> recoveryCodes = new HashMap<>();

    @Autowired
    public PasswordRecoveryService(UserRepo userRepo, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService; // Добавлено
    }

    // Отправка кода восстановления на email
    public void sendRecoveryCode(String email) {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Генерация случайного кода
        String code = String.valueOf(new Random().nextInt(999999));

        // Сохранение кода в памяти для проверки
        recoveryCodes.put(email, code);

        // Отправка email с кодом
        String subject = "Password Recovery Code";
        String text = "Your recovery code is: " + code;

        emailService.sendEmail(email, subject, text);
    }

    // Проверка кода восстановления
    public boolean verifyRecoveryCode(String email, String code) {
        return recoveryCodes.containsKey(email) && recoveryCodes.get(email).equals(code);
    }

    // Обновление пароля
    public void updatePassword(String email, String newPassword) {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}
