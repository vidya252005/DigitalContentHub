package com.contentHub.controller;

import com.contentHub.dto.SubscriptionDTO;
import com.contentHub.model.User;
import com.contentHub.pattern.chain.PlanHandlerChain;
import com.contentHub.service.AuthService;
import com.contentHub.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired private SubscriptionService subscriptionService;
    @Autowired private AuthService authService;

    /** Show all plans – always show all 3, mark current one as active */
    @GetMapping("/plans")
    public String viewPlans(Model model, Authentication auth) {
        model.addAttribute("freePlan",     PlanHandlerChain.fromPlanType("FREE"));
        model.addAttribute("monthlyPlan", PlanHandlerChain.fromPlanType("MONTHLY"));
        model.addAttribute("yearlyPlan",  PlanHandlerChain.fromPlanType("YEARLY"));

        if (auth != null) {
            User user = authService.findByUsername(auth.getName());
            subscriptionService.getSubscriptionByUserId(user.getId())
                    .ifPresent(sub -> model.addAttribute("currentSubscription", sub));
        }
        return "subscription/plans";
    }

    /**
     * Show payment page – redirect here from plan selection before actually subscribing.
     */
    @PostMapping("/checkout")
    public String checkout(@RequestParam String planType,
                           Authentication auth,
                           Model model) {
        double priceUsd = planType.equalsIgnoreCase("YEARLY") ? 79.99 : 9.99;
        long priceInr   = Math.round(priceUsd * 83);
        long taxInr     = Math.round(priceInr * 0.18);
        long totalInr   = priceInr + taxInr;

        model.addAttribute("planType", planType.toUpperCase());
        model.addAttribute("price",  priceInr);
        model.addAttribute("tax",    taxInr);
        model.addAttribute("total",  totalInr);
        return "subscription/payment";
    }

    /** Called by payment page form submit – actually creates the subscription */
    @PostMapping("/process-payment")
    public String processPayment(@RequestParam String planType,
                                 @RequestParam String paymentMethod,
                                 Authentication auth,
                                 RedirectAttributes redirectAttributes) {
        try {
            User user = authService.findByUsername(auth.getName());
            SubscriptionDTO dto = new SubscriptionDTO();
            dto.setPlanType(planType);
            dto.setPaymentMethod(paymentMethod);
            subscriptionService.subscribe(user.getId(), dto);
            redirectAttributes.addFlashAttribute("success",
                    "🎉 Payment successful! " + planType + " plan is now active.");
            return "redirect:/content"; // Redirect to catalog page instead of dashboard
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "❌ Payment failed: " + e.getMessage());
            return "redirect:/subscription/plans";
        }
    }

    /** Subscription status page */
    @GetMapping("/status")
    public String status(Authentication auth, Model model) {
        User user = authService.findByUsername(auth.getName());
        subscriptionService.getSubscriptionByUserId(user.getId()).ifPresent(sub -> {
            model.addAttribute("subscription", sub);
            model.addAttribute("planDetails", PlanHandlerChain.fromPlanType(sub.getPlanType()));
        });
        model.addAttribute("user", user);
        return "subscription/status";
    }

    /** Renew subscription */
    @PostMapping("/renew")
    public String renew(@RequestParam String paymentMethod,
                        Authentication auth,
                        RedirectAttributes redirectAttributes) {
        try {
            User user = authService.findByUsername(auth.getName());
            subscriptionService.renewSubscription(user.getId(), paymentMethod);
            redirectAttributes.addFlashAttribute("success", "✅ Subscription renewed successfully!");
            return "redirect:/content"; // Redirect to catalog instead of status
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "❌ Renewal failed: " + e.getMessage());
            return "redirect:/subscription/status";
        }
    }
}
