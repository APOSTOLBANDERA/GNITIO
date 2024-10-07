package com.example.gnitio.service;

import com.example.gnitio.entity.CourseEntity;
import com.example.gnitio.repository.CourseRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepo courseRepo;

    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    // 1. Создание нового курса
    public CourseEntity createCourse(@RequestBody CourseEntity course) {
        return courseRepo.save(course); // Сохраняем курс в базу данных и возвращаем его
    }

    // 2. Получение курса по ID
    public CourseEntity getCourseById(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Курс с ID " + id + " не найден")); // Если не найдено, выбрасываем исключение
    }

    // 3. Получение списка всех курсов
    public List<CourseEntity> getAllCourses() {
        return (List<CourseEntity>) courseRepo.findAll(); // Возвращаем все курсы
    }

    // 4. Обновление курса
    public CourseEntity updateCourse(Long id, CourseEntity courseDetails) {
        CourseEntity course = getCourseById(id); // Ищем курс по ID

        // Обновляем поля курса
        course.setTitle(courseDetails.getTitle());
        course.setMentor(courseDetails.getMentor());
        course.setDescription(courseDetails.getDescription());
        course.setDuration(courseDetails.getDuration());
        course.setFormat(courseDetails.getFormat());

        return courseRepo.save(course); // Сохраняем обновлённый курс в базу
    }

    // 5. Удаление курса
    public void deleteCourse(Long id) {
        CourseEntity course = getCourseById(id); // Ищем курс по ID
        courseRepo.delete(course); // Удаляем курс
    }
}