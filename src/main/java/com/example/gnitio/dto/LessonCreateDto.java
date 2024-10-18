package com.example.gnitio.dto;

import com.example.gnitio.entity.ModuleEntity;

public class LessonCreateDto {

    private String title;
    private String type;
    private String content;

    private ModuleEntity module;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ModuleEntity getModule() {
        return module;
    }

    public void setModule(ModuleEntity module) {
        this.module = module;
    }
}
