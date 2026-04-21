package com.contentHub.controller;

import com.contentHub.model.User;
import com.contentHub.service.AuthService;
import com.contentHub.service.ContentService;
import com.contentHub.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * MVC Controller – playlist creation, management, and sharing.
 */
@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired private PlaylistService playlistService;
    @Autowired private AuthService authService;
    @Autowired private ContentService contentService;

    @GetMapping
    public String myPlaylists(Authentication auth, Model model) {
        User user = authService.findByUsername(auth.getName());
        model.addAttribute("playlists", playlistService.getUserPlaylists(user.getId()));
        model.addAttribute("publicPlaylists", playlistService.getPublicPlaylists());
        return "playlist/index";
    }

    @GetMapping("/{id}")
    public String viewPlaylist(@PathVariable Long id, Authentication auth, Model model) {
        User user = authService.findByUsername(auth.getName());
        var playlist = playlistService.getPlaylistOrThrow(id);

        // Only owner can see private playlists
        if (!playlist.isPublic() && !playlist.getOwner().getId().equals(user.getId())) {
            return "redirect:/playlist?error=access";
        }

        model.addAttribute("playlist", playlist);
        model.addAttribute("isOwner", playlist.getOwner().getId().equals(user.getId()));
        model.addAttribute("allContent", contentService.getAllAvailableContent());
        return "playlist/view";
    }

    @PostMapping("/create")
    public String createPlaylist(@RequestParam String name,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(defaultValue = "false") boolean isPublic,
                                 Authentication auth,
                                 RedirectAttributes redirectAttributes) {
        User user = authService.findByUsername(auth.getName());
        playlistService.createPlaylist(user.getId(), name, description, isPublic);
        redirectAttributes.addFlashAttribute("success", "Playlist '" + name + "' created!");
        return "redirect:/playlist";
    }

    @PostMapping("/{id}/add/{contentId}")
    public String addContent(@PathVariable Long id,
                             @PathVariable Long contentId,
                             Authentication auth,
                             RedirectAttributes redirectAttributes) {
        User user = authService.findByUsername(auth.getName());
        playlistService.addContentToPlaylist(id, contentId, user.getId());
        redirectAttributes.addFlashAttribute("success", "Content added to playlist!");
        return "redirect:/playlist/" + id;
    }

    @PostMapping("/{id}/remove/{contentId}")
    public String removeContent(@PathVariable Long id,
                                @PathVariable Long contentId,
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        User user = authService.findByUsername(auth.getName());
        playlistService.removeContentFromPlaylist(id, contentId, user.getId());
        redirectAttributes.addFlashAttribute("success", "Content removed from playlist.");
        return "redirect:/playlist/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePlaylist(@PathVariable Long id,
                                 Authentication auth,
                                 RedirectAttributes redirectAttributes) {
        User user = authService.findByUsername(auth.getName());
        playlistService.deletePlaylist(id, user.getId());
        redirectAttributes.addFlashAttribute("success", "Playlist deleted.");
        return "redirect:/playlist";
    }
}
