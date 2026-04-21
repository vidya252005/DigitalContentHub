package com.contentHub.model;

import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    private String genre;
    private String creator;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_plan", nullable = false)
    private RequiredPlan requiredPlan = RequiredPlan.FREE;

    @Column(name = "view_count")
    private int viewCount = 0;

    private double rating = 0.0;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean available = true;

    @ManyToMany(mappedBy = "contentItems")
    private List<Playlist> playlists = new ArrayList<>();

    public Content() {}

    public void incrementViewCount() { this.viewCount++; }

    public boolean isPremium() { return requiredPlan != RequiredPlan.FREE; }

    public String getRequiredPlanLabel() {
        if (requiredPlan == null) return "Free";
        return switch (requiredPlan) {
            case FREE    -> "Free";
            case MONTHLY -> "Monthly+";
            case YEARLY  -> "Yearly Only";
        };
    }

    public String getRequiredPlanColor() {
        if (requiredPlan == null) return "plan-free";
        return switch (requiredPlan) {
            case FREE    -> "plan-free";
            case MONTHLY -> "plan-monthly";
            case YEARLY  -> "plan-yearly-badge";
        };
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ContentType getContentType() { return contentType; }
    public String getGenre() { return genre; }
    public String getCreator() { return creator; }
    public String getFileUrl() { return fileUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public RequiredPlan getRequiredPlan() { return requiredPlan; }
    public int getViewCount() { return viewCount; }
    public double getRating() { return rating; }
    public Long getCreatedBy() { return createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isAvailable() { return available; }
    public List<Playlist> getPlaylists() { return playlists; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setCreator(String creator) { this.creator = creator; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setRequiredPlan(RequiredPlan requiredPlan) { this.requiredPlan = requiredPlan; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public void setRating(double rating) { this.rating = rating; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private String title, description, genre, creator, fileUrl, thumbnailUrl;
        private ContentType contentType; private RequiredPlan requiredPlan = RequiredPlan.FREE;
        private int viewCount = 0; private double rating = 0.0;
        private Long createdBy; private LocalDateTime createdAt = LocalDateTime.now();
        private boolean available = true;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String t) { this.title = t; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder contentType(ContentType ct) { this.contentType = ct; return this; }
        public Builder genre(String g) { this.genre = g; return this; }
        public Builder creator(String c) { this.creator = c; return this; }
        public Builder fileUrl(String f) { this.fileUrl = f; return this; }
        public Builder thumbnailUrl(String t) { this.thumbnailUrl = t; return this; }
        public Builder requiredPlan(RequiredPlan rp) { this.requiredPlan = rp; return this; }
        public Builder viewCount(int v) { this.viewCount = v; return this; }
        public Builder rating(double r) { this.rating = r; return this; }
        public Builder createdBy(Long cb) { this.createdBy = cb; return this; }
        public Builder createdAt(LocalDateTime t) { this.createdAt = t; return this; }
        public Builder available(boolean a) { this.available = a; return this; }

        public Content build() {
            Content c = new Content();
            c.id = id; c.title = title; c.description = description;
            c.contentType = contentType; c.genre = genre; c.creator = creator;
            c.fileUrl = fileUrl; c.thumbnailUrl = thumbnailUrl;
            c.requiredPlan = requiredPlan; c.viewCount = viewCount; c.rating = rating;
            c.createdBy = createdBy; c.createdAt = createdAt; c.available = available;
            return c;
        }
    }
}
