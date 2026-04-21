package com.contentHub.model;

import com.contentHub.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Subscription subscription;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Playlist> playlists = new ArrayList<>();

    public User() {}

    public User(Long id, String username, String email, String password, UserRole role,
                boolean active, LocalDateTime createdAt) {
        this.id = id; this.username = username; this.email = email;
        this.password = password; this.role = role;
        this.active = active; this.createdAt = createdAt;
    }

    public boolean hasActiveSubscription() {
        return subscription != null && subscription.isActive()
                && subscription.getEndDate() != null
                && subscription.getEndDate().isAfter(LocalDate.now());
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Subscription getSubscription() { return subscription; }
    public List<Playlist> getPlaylists() { return playlists; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(UserRole role) { this.role = role; }
    public void setActive(boolean active) { this.active = active; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setSubscription(Subscription subscription) { this.subscription = subscription; }
    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private String username, email, password;
        private UserRole role; private boolean active = true;
        private LocalDateTime createdAt = LocalDateTime.now();
        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String u) { this.username = u; return this; }
        public Builder email(String e) { this.email = e; return this; }
        public Builder password(String p) { this.password = p; return this; }
        public Builder role(UserRole r) { this.role = r; return this; }
        public Builder active(boolean a) { this.active = a; return this; }
        public Builder createdAt(LocalDateTime t) { this.createdAt = t; return this; }
        public User build() {
            User u = new User();
            u.id = id; u.username = username; u.email = email;
            u.password = password; u.role = role; u.active = active; u.createdAt = createdAt;
            return u;
        }
    }
}
