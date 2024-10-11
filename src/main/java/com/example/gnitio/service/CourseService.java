package com.example.gnitio.service;

import com.example.gnitio.entity.CourseEntity;
import com.example.gnitio.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;




    public CourseEntity createCourse(@RequestBody CourseEntity course) {
        return courseRepo.save(course); // Сохраняем курс в базу данных и возвращаем его
    }


    public CourseEntity getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс с ID " + id + " не найден")); // Если не найдено, выбрасываем исключение
    }


    public List<CourseEntity> getAllCourses() {
        return (List<CourseEntity>) courseRepo.findAll(); // Возвращаем все курсы
    }


    public CourseEntity updateCourse(Long id, CourseEntity courseDetails) {
        CourseEntity course = getCourseById(id); // Ищем курс по ID

        // Обновляем поля курса
        course.setTitle(courseDetails.getTitle());
        course.setMentor(courseDetails.getMentor());
        course.setDescription(courseDetails.getDescription());
        course.setDuration(courseDetails.getDuration());
        course.setFormat(courseDetails.getFormat());

        return courseRepo.save(course);
    }


    public void deleteCourse(Long id) {
        CourseEntity course = getCourseById(id);
        courseRepo.delete(course);
    }
}