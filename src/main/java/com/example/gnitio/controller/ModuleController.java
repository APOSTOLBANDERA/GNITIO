package com.example.gnitio.controller;

import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // Добавление модуля в курс
    @PostMapping("/{courseId}/add")
    public ResponseEntity<ModuleEntity> addModuleToCourse(
            @PathVariable Long courseId,
            @RequestBody ModuleEntity module) {
        ModuleEntity createdModule = moduleService.addModuleToCourse(courseId, module);
        return ResponseEntity.ok(createdModule);
    }

    // Получение всех модулей для курса
    @GetMapping("/{courseId}")
    public ResponseEntity<List<ModuleEntity>> getModulesByCourseId(@PathVariable Long courseId) {
        List<ModuleEntity> modules = moduleService.getModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }
}
