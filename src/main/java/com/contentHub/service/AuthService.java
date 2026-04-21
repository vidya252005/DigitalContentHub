package com.contentHub.service;

import com.contentHub.dto.RegisterDTO;
import com.contentHub.enums.UserRole;
import com.contentHub.exception.ContentHubException;
import com.contentHub.model.User;
import com.contentHub.pattern.factory.UserFactory;
import com.contentHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserFactory userFactory;

    public User registerSubscriber(RegisterDTO dto) {
        validateRegistration(dto);
        User user = userFactory.createSubscriber(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return userRepository.save(user);
    }

    public User registerAdmin(RegisterDTO dto) {
        validateRegistration(dto);
        User user = userFactory.createAdmin(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return userRepository.save(user);
    }

    public User registerCurator(RegisterDTO dto) {
        validateRegistration(dto);
        User user = userFactory.createCurator(dto.getUsername(), dto.getEmail(), dto.getPassword());
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ContentHubException("User not found: " + username));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ContentHubException("User not found: " + userId));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    private void validateRegistration(RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new ContentHubException("Username already taken: " + dto.getUsername());
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new ContentHubException("Email already registered: " + dto.getEmail());
    }
}
