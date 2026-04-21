package com.contentHub.repository;

import com.contentHub.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByOwnerId(Long ownerId);

    List<Playlist> findByIsPublicTrue();

    List<Playlist> findByOwnerIdOrIsPublicTrue(Long ownerId);
}
