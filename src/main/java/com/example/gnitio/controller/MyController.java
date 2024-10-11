package com.example.gnitio.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class MyController {

    // Класс для хранения данных, приходящих с клиента
    public static class MyRequest {
        private String name;
        private int age;

        // Геттеры и сеттеры
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @PostMapping("/postData")
    public ResponseEntity<String> postData(@RequestBody MyRequest request) {
        // Обрабатываем полученные данные]
        System.out.println("alaxacbar");
        String responseMessage = "Received name: " + request.getName() + ", age: " + request.getAge();

        // Возвращаем успешный ответ
        return ResponseEntity.ok(responseMessage);
    }
}
