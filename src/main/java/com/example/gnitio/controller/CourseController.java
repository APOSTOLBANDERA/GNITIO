package com.example.gnitio.controller;

import com.example.gnitio.entity.CourseEntity;
import com.example.gnitio.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/courses")
@RestController
 // Базовый путь для всех операций с курсами
public class CourseController {


    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping
    public ResponseEntity<CourseEntity> createCourse(@RequestBody CourseEntity course) {
        //System.out.println("Request received to create a course");
        CourseEntity createdCourse = courseService.createCourse(course);
        System.out.println("Created Course: " + createdCourse);
        return ResponseEntity.ok(createdCourse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseById(@PathVariable Long id) {
        CourseEntity course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    // 3. Получение списка всех курсов
    @GetMapping
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        List<CourseEntity> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses); // Возвращаем список курсов
    }

    // 4. Обновление курса
    //@PutMapping("/{id}")
    //public ResponseEntity<CourseEntity> updateCourse(
            //@PathVariable Long id,
            //@RequestBody CourseEntity courseDetails
    //) {
        //CourseEntity updatedCourse = courseService.updateCourse(id, courseDetails);
        //return ResponseEntity.ok(updatedCourse); // Возвращаем обновленный курс
    //}

    // 5. Удаление курса
    //@DeleteMapping("/{id}")
    //public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        //courseService.deleteCourse(id);
        //return ResponseEntity.noContent().build(); // Возвращаем 204 No Content при успешном удалении
    //}
}
