package com.example.gnitio.service;

import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.entity.CourseEntity;
import com.example.gnitio.repository.ModuleRepo;
import com.example.gnitio.repository.CourseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepo moduleRepo;
    private final CourseRepo courseRepo;

    public ModuleService(ModuleRepo moduleRepo, CourseRepo courseRepo) {
        this.moduleRepo = moduleRepo;
        this.courseRepo = courseRepo;
    }

    public ModuleEntity addModuleToCourse(Long courseId, ModuleEntity module) {
        CourseEntity course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        module.setCourse(course);
        return moduleRepo.save(module);
    }

    public List<ModuleEntity> getModulesByCourseId(Long courseId) {
        CourseEntity course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return moduleRepo.findByCourse(course);
    }
}