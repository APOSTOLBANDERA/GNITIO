package com.example.gnitio.service;

import com.example.gnitio.entity.EventEntity;
import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.repository.EventRepo;
import com.example.gnitio.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;

    @Autowired
    public EventService(EventRepo eventRepo, UserRepo userRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    public EventEntity createEvent(Long userId, EventEntity event) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        event.setUser(user);
        return eventRepo.save(event);
    }

    public List<EventEntity> getEventsByUser(Long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return eventRepo.findByUser(user);
    }

    public List<EventEntity> getEventsForUserBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return eventRepo.findByUserAndStartTimeBetween(user, start, end);
    }

    public EventEntity updateEvent(Long eventId, EventEntity updatedEvent) {
        EventEntity event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setStartTime(updatedEvent.getStartTime());
        event.setEndTime(updatedEvent.getEndTime());
        return eventRepo.save(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepo.deleteById(eventId);
    }
}
