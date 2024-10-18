package com.example.gnitio.service;

import com.example.gnitio.dto.LessonCreateDto;
import com.example.gnitio.entity.CourseEntity;
import com.example.gnitio.entity.LessonEntity;
import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.repository.LessonRepo;
import com.example.gnitio.repository.ModuleRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepo lessonRepo;
    private final ModuleRepo moduleRepo;

    public LessonService(LessonRepo lessonRepo, ModuleRepo moduleRepo) {
        this.lessonRepo = lessonRepo;
        this.moduleRepo = moduleRepo;
    }

    public LessonEntity addLessonToModule(Long moduleId, LessonCreateDto lessonCreateDto) {
        ModuleEntity module = moduleRepo.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found"));

        LessonEntity lesson = new LessonEntity();
        lesson.setTitle(lessonCreateDto.getTitle());
        lesson.setType(lessonCreateDto.getType());
        lesson.setContent(lessonCreateDto.getContent());
        lesson.setModule(module);

        return lessonRepo.save(lesson);
    }

    public void uploadFileToLesson(Long lessonId, MultipartFile file) throws IOException {
        Optional<LessonEntity> lessonOptional = lessonRepo.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new IllegalArgumentException("Lesson not found");
        }

        LessonEntity lesson = lessonOptional.get();
        lesson.setFileData(file.getBytes());
        lesson.setFileName(file.getOriginalFilename());

        lessonRepo.save(lesson);
    }

    public LessonEntity getLessonById(Long lessonId) {
        return lessonRepo.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
    }
}