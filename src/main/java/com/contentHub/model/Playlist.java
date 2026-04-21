package com.contentHub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
        name = "playlist_content",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "content_id")
    )
    private List<Content> contentItems = new ArrayList<>();

    public Playlist() {}

    public void addContent(Content content) { contentItems.add(content); }
    public void removeContent(Content content) { contentItems.remove(content); }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public User getOwner() { return owner; }
    public boolean isPublic() { return isPublic; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Content> getContentItems() { return contentItems; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setOwner(User owner) { this.owner = owner; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setContentItems(List<Content> contentItems) { this.contentItems = contentItems; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private String name, description;
        private User owner; private boolean isPublic = false;
        private LocalDateTime createdAt = LocalDateTime.now();
        private List<Content> contentItems = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String n) { this.name = n; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder owner(User o) { this.owner = o; return this; }
        public Builder isPublic(boolean p) { this.isPublic = p; return this; }
        public Builder createdAt(LocalDateTime t) { this.createdAt = t; return this; }
        public Builder contentItems(List<Content> items) { this.contentItems = items; return this; }

        public Playlist build() {
            Playlist p = new Playlist();
            p.id = id; p.name = name; p.description = description;
            p.owner = owner; p.isPublic = isPublic; p.createdAt = createdAt;
            p.contentItems = contentItems;
            return p;
        }
    }
}
