package com.contentHub.controller;

import com.contentHub.dto.RegisterDTO;
import com.contentHub.exception.ContentHubException;
import com.contentHub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error  != null) model.addAttribute("error",   "Invalid username or password.");
        if (logout != null) model.addAttribute("message", "You have been logged out.");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDTO") RegisterDTO dto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "auth/register";

        if (!dto.passwordsMatch()) {
            result.rejectValue("confirmPassword", "error.dto", "Passwords do not match");
            return "auth/register";
        }

        try {
            if (dto.isAdminRole()) {
                // Register as Admin
                authService.registerAdmin(dto);
                redirectAttributes.addFlashAttribute("success",
                        "Admin account created! Please log in.");
            } else {
                // Register as Subscriber
                authService.registerSubscriber(dto);
                redirectAttributes.addFlashAttribute("success",
                        "Account created! Please log in.");
            }
            return "redirect:/auth/login";
        } catch (ContentHubException e) {
            result.rejectValue("username", "error.dto", e.getMessage());
            return "auth/register";
        }
    }
}
