package com.example.gnitio.repository;

import com.example.gnitio.entity.EventEntity;
import com.example.gnitio.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByUser(UserEntity user);
    List<EventEntity> findByUserAndStartTimeBetween(UserEntity user, LocalDateTime start, LocalDateTime end);
}
