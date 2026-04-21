package com.contentHub.pattern.factory;

import com.contentHub.enums.UserRole;
import com.contentHub.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * =====================================================
 * DESIGN PATTERN 2: FACTORY PATTERN (Creational)
 * =====================================================
 *
 * Intent: Define an interface for creating objects, but let subclasses
 * (or the factory itself) decide which class to instantiate.
 *
 * In this system:
 * UserFactory centralises the creation of User objects with different
 * roles (SUBSCRIBER, CURATOR, ADMIN). This avoids scattered `new User()`
 * calls with role setup scattered across the codebase.
 *
 * Design Principle: Dependency Inversion – high-level modules (services)
 * depend on this factory abstraction, not on direct User construction.
 *
 * How it maps to our domain:
 * When a new user registers, the system calls UserFactory with the role.
 * The factory ensures correct defaults, encodes the password, and returns
 * a fully initialised User object ready to be persisted.
 */
@Component
public class UserFactory {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserFactory(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Factory method – creates a User with the appropriate role and defaults.
     */
    public User createUser(String username, String email, String rawPassword, UserRole role) {
        String encodedPassword = passwordEncoder.encode(rawPassword);

        return User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .role(role)
                .active(true)
                .build();
    }

    /** Convenience factory method for creating a Subscriber */
    public User createSubscriber(String username, String email, String rawPassword) {
        return createUser(username, email, rawPassword, UserRole.SUBSCRIBER);
    }

    /** Convenience factory method for creating a Content Curator */
    public User createCurator(String username, String email, String rawPassword) {
        return createUser(username, email, rawPassword, UserRole.CURATOR);
    }

    /** Convenience factory method for creating an Administrator */
    public User createAdmin(String username, String email, String rawPassword) {
        return createUser(username, email, rawPassword, UserRole.ADMIN);
    }
}
