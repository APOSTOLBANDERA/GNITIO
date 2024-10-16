package com.example.gnitio.entity;

import com.example.gnitio.util.CourseFormat;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String mentor;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseFormat format;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleEntity> modules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CourseFormat getFormat() {
        return format;
    }

    public void setFormat(CourseFormat format) {
        this.format = format;
    }

    public List<ModuleEntity> getModules() {
        return modules;
    }

    public void setModules(List<ModuleEntity> modules) {
        this.modules = modules;
    }

}
