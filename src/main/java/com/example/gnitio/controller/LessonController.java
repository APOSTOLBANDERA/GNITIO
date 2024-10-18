package com.example.gnitio.controller;

import com.example.gnitio.entity.LessonEntity;
import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }


    @PostMapping("/{moduleId}/add")
    public ResponseEntity<LessonEntity> addLessonToModule(
            @PathVariable Long moduleId,
            @RequestBody LessonEntity lesson) {
        LessonEntity createdLesson = lessonService.addLessonToModule(moduleId, lesson);
        return ResponseEntity.ok(createdLesson);
    }


    @PostMapping("/{lessonId}/upload")
    public ResponseEntity<String> uploadFileToLesson(
            @PathVariable Long lessonId,
            @RequestParam("file") MultipartFile file) {
        try {
            lessonService.uploadFileToLesson(lessonId, file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }
    @GetMapping("/{lessonId}/download")
    public ResponseEntity<byte[]> downloadFileFromLesson(@PathVariable Long lessonId) {
        LessonEntity lesson = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + lesson.getFileName() + "\"")
                .body(lesson.getFileData());
    }
}
