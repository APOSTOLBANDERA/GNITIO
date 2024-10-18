package com.example.gnitio.controller;

import com.example.gnitio.entity.EventEntity;
import com.example.gnitio.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/user/{userId}/create")
    public ResponseEntity<EventEntity> createEvent(
            @PathVariable Long userId,
            @RequestBody EventEntity event) {
        EventEntity createdEvent = eventService.createEvent(userId, event);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventEntity>> getUserEvents(@PathVariable Long userId) {
        List<EventEntity> events = eventService.getEventsByUser(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/user/{userId}/between")
    public ResponseEntity<List<EventEntity>> getUserEventsBetween(
            @PathVariable Long userId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<EventEntity> events = eventService.getEventsForUserBetween(userId, start, end);
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventEntity> updateEvent(
            @PathVariable Long eventId,
            @RequestBody EventEntity updatedEvent) {
        EventEntity event = eventService.updateEvent(eventId, updatedEvent);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
