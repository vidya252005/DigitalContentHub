package com.contentHub.controller;

import com.contentHub.model.User;
import com.contentHub.enums.UserRole;
import com.contentHub.service.AuthService;
import com.contentHub.service.ContentService;
import com.contentHub.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired private AuthService authService;
    @Autowired private ContentService contentService;
    @Autowired private SubscriptionService subscriptionService;

    @GetMapping("/")
    public String home(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) return "redirect:/dashboard";
        return "redirect:/auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = authService.findByUsername(auth.getName());

        // Redirect admins straight to admin panel
        if (user.getRole() == UserRole.ADMIN) {
            return "redirect:/admin";
        }

        model.addAttribute("user", user);
        model.addAttribute("trending", contentService.getTrendingContent().stream().limit(4).toList());
        subscriptionService.getSubscriptionByUserId(user.getId())
                .ifPresent(sub -> model.addAttribute("subscription", sub));

        return "dashboard";
    }
}
