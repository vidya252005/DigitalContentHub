package com.contentHub.service;

import com.contentHub.exception.ContentHubException;
import com.contentHub.model.Content;
import com.contentHub.model.Playlist;
import com.contentHub.model.User;
import com.contentHub.repository.ContentRepository;
import com.contentHub.repository.PlaylistRepository;
import com.contentHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Manages personal libraries and collaborative playlists.
 * Enforces privacy: private playlists are only visible to their owner.
 */
@Service
@Transactional
public class PlaylistService {

    @Autowired private PlaylistRepository playlistRepository;
    @Autowired private ContentRepository contentRepository;
    @Autowired private UserRepository userRepository;

    public Playlist createPlaylist(Long ownerId, String name, String description, boolean isPublic) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ContentHubException("User not found"));

        Playlist playlist = Playlist.builder()
                .name(name)
                .description(description)
                .owner(owner)
                .isPublic(isPublic)
                .build();

        return playlistRepository.save(playlist);
    }

    public Playlist addContentToPlaylist(Long playlistId, Long contentId, Long requestingUserId) {
        Playlist playlist = getPlaylistOrThrow(playlistId);
        assertOwner(playlist, requestingUserId);

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentHubException("Content not found"));

        if (!playlist.getContentItems().contains(content)) {
            playlist.addContent(content);
        }
        return playlistRepository.save(playlist);
    }

    public Playlist removeContentFromPlaylist(Long playlistId, Long contentId, Long requestingUserId) {
        Playlist playlist = getPlaylistOrThrow(playlistId);
        assertOwner(playlist, requestingUserId);

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentHubException("Content not found"));

        playlist.removeContent(content);
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long playlistId, Long requestingUserId) {
        Playlist playlist = getPlaylistOrThrow(playlistId);
        assertOwner(playlist, requestingUserId);
        playlistRepository.delete(playlist);
    }

    public List<Playlist> getUserPlaylists(Long userId) {
        return playlistRepository.findByOwnerId(userId);
    }

    public List<Playlist> getAccessiblePlaylists(Long userId) {
        return playlistRepository.findByOwnerIdOrIsPublicTrue(userId);
    }

    public List<Playlist> getPublicPlaylists() {
        return playlistRepository.findByIsPublicTrue();
    }

    public Playlist getPlaylistOrThrow(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ContentHubException("Playlist not found: " + id));
    }

    private void assertOwner(Playlist playlist, Long userId) {
        if (!playlist.getOwner().getId().equals(userId)) {
            throw new ContentHubException("You do not own this playlist");
        }
    }
}
