package com.contentHub.repository;

import com.contentHub.enums.UserRole;
import com.contentHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Layer for User entity.
 * Spring Data JPA auto-implements these methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByActiveTrue();

    long countByRole(UserRole role);
}
