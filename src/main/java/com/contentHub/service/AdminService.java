package com.contentHub.service;

import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import com.contentHub.enums.UserRole;
import com.contentHub.model.User;
import com.contentHub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AdminService {

    @Autowired private UserRepository userRepository;
    @Autowired private ContentRepository contentRepository;
    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private PaymentRepository paymentRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers",            userRepository.count());
        stats.put("totalSubscribers",      userRepository.countByRole(UserRole.SUBSCRIBER));
        stats.put("totalCurators",         userRepository.countByRole(UserRole.CURATOR));
        stats.put("totalContent",          contentRepository.count());
        stats.put("totalMovies",           contentRepository.countByContentType(ContentType.MOVIE));
        stats.put("totalMusic",            contentRepository.countByContentType(ContentType.MUSIC));
        stats.put("totalEbooks",           contentRepository.countByContentType(ContentType.EBOOK));
        stats.put("freeContent",           contentRepository.countByRequiredPlan(RequiredPlan.FREE));
        stats.put("monthlyContent",        contentRepository.countByRequiredPlan(RequiredPlan.MONTHLY));
        stats.put("yearlyContent",         contentRepository.countByRequiredPlan(RequiredPlan.YEARLY));
        stats.put("activeSubscriptions",   subscriptionRepository.findByActiveTrue().size());
        stats.put("monthlySubscriptions",  subscriptionRepository.countByPlanType("MONTHLY"));
        stats.put("yearlySubscriptions",   subscriptionRepository.countByPlanType("YEARLY"));
        stats.put("totalRevenue",          paymentRepository.getTotalRevenue());
        stats.put("trendingContent",       contentRepository.findTopByViewCount().stream().limit(5).toList());
        return stats;
    }

    /** Returns all users; subscription and playlists are loaded within this transaction. */
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Touch lazy collections within the transaction to avoid LazyInitializationException in templates
        users.forEach(u -> {
            if (u.getSubscription() != null) u.getSubscription().getPlanType();
            u.getPlaylists().size();
        });
        return users;
    }

    public List<User> getUsersByRole(UserRole role) { return userRepository.findByRole(role); }
}
