package com.contentHub.controller;

import com.contentHub.dto.ContentDTO;
import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import com.contentHub.model.Playlist;
import com.contentHub.model.User;
import com.contentHub.pattern.proxy.AccessResult;
import com.contentHub.service.AuthService;
import com.contentHub.service.ContentService;
import com.contentHub.service.PlaylistService;
import com.contentHub.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired private ContentService contentService;
    @Autowired private AuthService authService;
    @Autowired private PlaylistService playlistService;
    @Autowired private SubscriptionService subscriptionService;

    @GetMapping
    @Transactional(readOnly = true)
    public String catalog(@RequestParam(required = false) String type,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String plan,
                          Authentication auth, Model model) {

        if (search != null && !search.isBlank()) {
            model.addAttribute("contents", contentService.searchContent(search));
            model.addAttribute("searchQuery", search);
        } else if (plan != null && !plan.isBlank()) {
            try {
                model.addAttribute("contents",
                    contentService.getContentByRequiredPlan(RequiredPlan.valueOf(plan.toUpperCase())));
                model.addAttribute("planFilter", plan.toUpperCase());
            } catch (IllegalArgumentException e) {
                model.addAttribute("contents", contentService.getAllAvailableContent());
            }
        } else if (type != null && !type.isBlank()) {
            try {
                model.addAttribute("contents",
                    contentService.getContentByType(ContentType.valueOf(type.toUpperCase())));
                model.addAttribute("activeType", type.toUpperCase());
            } catch (IllegalArgumentException e) {
                model.addAttribute("contents", contentService.getAllAvailableContent());
            }
        } else {
            model.addAttribute("contents", contentService.getAllAvailableContent());
        }
        model.addAttribute("contentTypes", ContentType.values());

        if (auth != null) {
            try {
                User user = authService.findByUsername(auth.getName());
                subscriptionService.getSubscriptionByUserId(user.getId())
                        .ifPresent(sub -> model.addAttribute("userSubscription", sub));

                List<Playlist> playlists = playlistService.getUserPlaylists(user.getId());
                List<Map<String, Object>> pls = new ArrayList<>();
                for (Playlist p : playlists) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    m.put("public", p.isPublic());
                    pls.add(m);
                }
                model.addAttribute("userPlaylists", pls);
            } catch (Exception ignored) {
                model.addAttribute("userPlaylists", Collections.emptyList());
            }
        }
        return "content/catalog";
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String viewContent(@PathVariable Long id, Authentication auth, Model model) {
        model.addAttribute("content", contentService.getContentById(id));
        if (auth != null) {
            try {
                User user = authService.findByUsername(auth.getName());
                List<Playlist> playlists = playlistService.getUserPlaylists(user.getId());
                model.addAttribute("userPlaylists", playlists);
                subscriptionService.getSubscriptionByUserId(user.getId())
                        .ifPresent(sub -> model.addAttribute("userSubscription", sub));
            } catch (Exception ignored) {}
        }
        return "content/detail";
    }

    @GetMapping("/{id}/stream")
    @Transactional(readOnly = true)
    public String streamContent(@PathVariable Long id, Authentication auth,
                                Model model, RedirectAttributes redirectAttributes) {
        User user = authService.findByUsername(auth.getName());
        AccessResult result = contentService.accessContent(user, id);
        if (result.isGranted()) {
            model.addAttribute("content", contentService.getContentById(id));
            return "content/stream";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getMessage());
            return "redirect:/subscription/plans";
        }
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('CURATOR','ADMIN')")
    public String addContentForm(Model model) {
        model.addAttribute("contentDTO", new ContentDTO());
        model.addAttribute("contentTypes", ContentType.values());
        return "content/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('CURATOR','ADMIN')")
    public String addContent(@Valid @ModelAttribute("contentDTO") ContentDTO dto,
                             BindingResult result, Authentication auth,
                             RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("contentTypes", ContentType.values());
            return "content/add";
        }
        User curator = authService.findByUsername(auth.getName());
        contentService.addContent(dto, curator.getId());
        redirectAttributes.addFlashAttribute("success", "Content added!");
        return "redirect:/content";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('CURATOR','ADMIN')")
    public String editContentForm(@PathVariable Long id, Model model) {
        model.addAttribute("content", contentService.getContentById(id));
        model.addAttribute("contentDTO", new ContentDTO());
        model.addAttribute("contentTypes", ContentType.values());
        return "content/edit";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('CURATOR','ADMIN')")
    public String editContent(@PathVariable Long id,
                              @Valid @ModelAttribute("contentDTO") ContentDTO dto,
                              BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "content/edit";
        contentService.updateContent(id, dto);
        redirectAttributes.addFlashAttribute("success", "Content updated!");
        return "redirect:/content/" + id;
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('CURATOR','ADMIN')")
    public String deleteContent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contentService.removeContent(id);
        redirectAttributes.addFlashAttribute("success", "Content removed.");
        return "redirect:/content";
    }
}
