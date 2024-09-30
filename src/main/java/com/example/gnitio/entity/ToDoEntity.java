package com.example.gnitio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class ToDoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Boolean completed;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ToDoEntity(){

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
