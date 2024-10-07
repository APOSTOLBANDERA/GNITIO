package com.example.gnitio.controller;

import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/{moduleId}/upload")
    public ResponseEntity<String> uploadFile(
            @PathVariable Long moduleId,
            @RequestParam("file") MultipartFile file
    ) {
        try {

            String filePath = moduleService.uploadFile(moduleId, file);
            return ResponseEntity.ok("Файл загружен: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Ошибка загрузки файла: " + e.getMessage());
        }
    }
}
