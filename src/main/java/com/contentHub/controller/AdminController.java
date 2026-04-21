package com.contentHub.controller;

import com.contentHub.dto.ContentDTO;
import com.contentHub.dto.RegisterDTO;
import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import com.contentHub.model.Content;
import com.contentHub.service.AdminService;
import com.contentHub.service.AuthService;
import com.contentHub.service.ContentService;
import com.contentHub.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private AuthService authService;
    @Autowired private ContentService contentService;
    @Autowired private SubscriptionService subscriptionService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("stats", adminService.getDashboardStats());
        return "admin/dashboard";
    }

    // ── Content Management ──────────────────────────────

    @GetMapping("/content")
    public String manageContent(@RequestParam(required = false) String plan, Model model) {
        if (plan != null && !plan.isBlank()) {
            try {
                RequiredPlan rp = RequiredPlan.valueOf(plan.toUpperCase());
                model.addAttribute("contents", contentService.getContentByRequiredPlan(rp));
                model.addAttribute("planFilter", plan.toUpperCase());
            } catch (IllegalArgumentException e) {
                model.addAttribute("contents", contentService.getAllContentForAdmin());
            }
        } else {
            model.addAttribute("contents", contentService.getAllContentForAdmin());
        }
        return "admin/content";
    }

    @GetMapping("/content/upload")
    public String uploadForm(Model model) {
        model.addAttribute("contentDTO", new ContentDTO());
        model.addAttribute("contentTypes", ContentType.values());
        return "admin/upload";
    }

    @PostMapping("/content/upload")
    public String uploadContent(@Valid @ModelAttribute("contentDTO") ContentDTO dto,
                                BindingResult result, Authentication auth,
                                RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("contentTypes", ContentType.values());
            return "admin/upload";
        }
        var admin = authService.findByUsername(auth.getName());
        contentService.addContent(dto, admin.getId());
        redirectAttributes.addFlashAttribute("success", "\"" + dto.getTitle() + "\" uploaded!");
        return "redirect:/admin/content";
    }

    @GetMapping("/content/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Content c = contentService.getContentById(id);
        ContentDTO dto = new ContentDTO();
        dto.setTitle(c.getTitle());
        dto.setDescription(c.getDescription());
        dto.setGenre(c.getGenre());
        dto.setCreator(c.getCreator());
        dto.setContentType(c.getContentType());
        dto.setRequiredPlan(c.getRequiredPlan());
        dto.setAvailable(c.isAvailable());
        model.addAttribute("content", c);
        model.addAttribute("contentDTO", dto);
        model.addAttribute("contentTypes", ContentType.values());
        return "admin/edit-content";
    }

    @PostMapping("/content/{id}/edit")
    public String editContent(@PathVariable Long id,
                              @Valid @ModelAttribute("contentDTO") ContentDTO dto,
                              BindingResult result, RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("content", contentService.getContentById(id));
            model.addAttribute("contentTypes", ContentType.values());
            return "admin/edit-content";
        }
        Content updated = contentService.updateContent(id, dto);
        redirectAttributes.addFlashAttribute("success", "\"" + updated.getTitle() + "\" updated.");
        return "redirect:/admin/content";
    }

    @PostMapping("/content/{id}/toggle")
    public String toggleContent(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Content c = contentService.getContentById(id);
        boolean newState = !c.isAvailable();

        ContentDTO dto = new ContentDTO();
        dto.setTitle(c.getTitle());
        dto.setDescription(c.getDescription());
        dto.setGenre(c.getGenre());
        dto.setCreator(c.getCreator());
        dto.setContentType(c.getContentType());
        dto.setRequiredPlan(c.getRequiredPlan());
        dto.setAvailable(newState);

        contentService.updateContent(id, dto);

        redirectAttributes.addFlashAttribute("success",
                "\"" + c.getTitle() + "\" is now " + (newState ? "visible" : "hidden") + ".");

        return "redirect:/admin/content";
}

    @PostMapping("/content/{id}/delete")
    public String deleteContent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Content c = contentService.getContentById(id);
        contentService.hardDeleteContent(id);
        redirectAttributes.addFlashAttribute("success", "\"" + c.getTitle() + "\" permanently deleted.");
        return "redirect:/admin/content";
    }

    @GetMapping("/curators/add")
    public String addCuratorForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "admin/add-curator";
    }

    @PostMapping("/curators/add")
    public String addCurator(@Valid @ModelAttribute("registerDTO") RegisterDTO dto,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "admin/add-curator";
        try {
            authService.registerCurator(dto);
            redirectAttributes.addFlashAttribute("success", "Curator account created!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/subscriptions")
    public String subscriptions(Model model) {
        model.addAttribute("subscriptions", subscriptionService.getAllSubscriptions());
        return "admin/subscriptions";
    }
}
