package com.example.gnitio.repository;

import com.example.gnitio.entity.LessonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepo extends CrudRepository<LessonEntity, Long> {
}
