package com.example.gnitio.repository;

import com.example.gnitio.entity.ToDoEntity;
import com.example.gnitio.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface ToDoRepo extends CrudRepository<ToDoEntity, Long> {
}
