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

        Optional<ModuleEntity> moduleOptional = moduleRepo.findById(moduleId);
        if (!moduleOptional.isPresent()) {
            throw new IllegalArgumentException("Модуль не найден");
        }

        ModuleEntity module = moduleOptional.get();


        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());


        module.setFilePath(filePath.toString());
        moduleRepo.save(module);

        return filePath.toString();
    }
}
