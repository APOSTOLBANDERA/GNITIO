package com.example.gnitio.repository;

import com.example.gnitio.entity.CourseEntity;
import com.example.gnitio.entity.ModuleEntity;
import com.example.gnitio.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepo extends CrudRepository<ModuleEntity, Long> {
    List<ModuleEntity> findByCourse(CourseEntity course);
}