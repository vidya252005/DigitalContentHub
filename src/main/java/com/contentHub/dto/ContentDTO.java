package com.contentHub.dto;

import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContentDTO {

    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @NotNull(message = "Content type is required")
    private ContentType contentType;

    private String genre;

    @NotBlank(message = "Creator name is required")
    private String creator;

    private String fileUrl;
    private String thumbnailUrl;
    private RequiredPlan requiredPlan = RequiredPlan.FREE;
    private boolean available = true;

    public ContentDTO() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public RequiredPlan getRequiredPlan() { return requiredPlan; }
    public void setRequiredPlan(RequiredPlan requiredPlan) { this.requiredPlan = requiredPlan; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
