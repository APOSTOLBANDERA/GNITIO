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
    private final Map<String, String> recoveryCodes = new HashMap<>(); // Коды восстановления для каждого email

    @Autowired
    public PasswordRecoveryService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Отправка кода восстановления на email
    public void sendRecoveryCode(String email) {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Генерация случайного кода
        String code = String.valueOf(new Random().nextInt(999999));

        // Здесь можно добавить логику для отправки email с кодом восстановления

        recoveryCodes.put(email, code);
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
