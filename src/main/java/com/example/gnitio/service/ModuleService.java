package com.example.gnitio.service;

import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.repository.ModuleRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepo moduleRepo;

    public ModuleService(ModuleRepo moduleRepo) {
        this.moduleRepo = moduleRepo;
    }

    private final String uploadDir = "uploads/";

    public String uploadFile(Long moduleId, MultipartFile file) throws IOException {
        // Проверяем, существует ли модуль
        Optional<ModuleEntity> moduleOptional = moduleRepo.findById(moduleId);
        if (!moduleOptional.isPresent()) {
            throw new IllegalArgumentException("Модуль не найден");
        }

        ModuleEntity module = moduleOptional.get();

        // Создаем директорию для загрузки файлов, если её нет
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Сохраняем файл на сервере
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        // Обновляем модуль, добавляем путь к файлу
        module.setFilePath(filePath.toString());
        moduleRepo.save(module);

        return filePath.toString();
    }
}
