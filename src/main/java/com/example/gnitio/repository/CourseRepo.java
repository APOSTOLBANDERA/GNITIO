package com.example.gnitio.repository;

import com.example.gnitio.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends CrudRepository<CourseEntity, Long> {
}
