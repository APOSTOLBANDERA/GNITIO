package com.example.gnitio.repository;

import com.example.gnitio.entity.ModuleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepo extends CrudRepository<ModuleEntity, Long> {
}