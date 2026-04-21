package com.contentHub.service;

import com.contentHub.dto.SubscriptionDTO;
import com.contentHub.exception.ContentHubException;
import com.contentHub.model.Payment;
import com.contentHub.model.Subscription;
import com.contentHub.model.User;
import com.contentHub.pattern.chain.PlanHandlerChain;
import com.contentHub.pattern.chain.SubscriptionPlan;
import com.contentHub.pattern.command.ActivateSubscriptionCommand;
import com.contentHub.pattern.command.ExpireSubscriptionCommand;
import com.contentHub.pattern.command.RenewSubscriptionCommand;
import com.contentHub.pattern.command.SubscriptionCommandInvoker;
import com.contentHub.repository.PaymentRepository;
import com.contentHub.repository.SubscriptionRepository;
import com.contentHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Manages subscription lifecycle: creation, renewal, expiry, payment.
 *
 * Uses:
 *  - Chain of Responsibility Pattern (PlanHandlerChain) to resolve plan features and pricing
 *  - Command Pattern (SubscriptionCommandInvoker) to execute subscription lifecycle actions
 */
@Service
@Transactional
public class SubscriptionService {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private SubscriptionCommandInvoker commandInvoker; // COMMAND PATTERN

    /**
     * Creates a new subscription for a user.
     * Uses Chain of Responsibility to resolve plan features.
     * Uses Command Pattern to execute activation side-effects.
     */
    public Subscription subscribe(Long userId, SubscriptionDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ContentHubException("User not found"));

        // Reuse existing subscription row if present, otherwise create a new one.
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .map(existing -> {
                    existing.setActive(true);
                    existing.setPlanType(dto.getPlanType().toUpperCase());
                    existing.setStartDate(LocalDate.now());
                    existing.setEndDate(dto.getPlanType().equalsIgnoreCase("YEARLY")
                            ? LocalDate.now().plusYears(1)
                            : LocalDate.now().plusMonths(1));
                    existing.setPrice(calculateSubscriptionPrice(dto.getPlanType()));
                    existing.setPaymentStatus("PAID");
                    existing.setRenewedAt(LocalDateTime.now());
                    return existing;
                })
                .orElseGet(() -> {
                    SubscriptionPlan plan = PlanHandlerChain.fromPlanType(dto.getPlanType());
                    if (plan == null) {
                        throw new ContentHubException("Invalid plan type: " + dto.getPlanType());
                    }
                    LocalDate start = LocalDate.now();
                    LocalDate end = dto.getPlanType().equalsIgnoreCase("YEARLY")
                            ? start.plusYears(1)
                            : start.plusMonths(1);

                    return Subscription.builder()
                            .user(user)
                            .planType(dto.getPlanType().toUpperCase())
                            .startDate(start)
                            .endDate(end)
                            .active(true)
                            .price(calculateSubscriptionPrice(dto.getPlanType()))
                            .paymentStatus("PAID")
                            .build();
                });

        Subscription saved = subscriptionRepository.save(subscription);

        // Record payment
        recordPayment(userId, saved.getId(), saved.getPrice(), dto.getPaymentMethod());

        // COMMAND PATTERN: execute activation command
        try {
            commandInvoker.invoke(new ActivateSubscriptionCommand(saved));
        } catch (Exception e) {
            // Log the error but don't fail the subscription
            logger.warn("Failed to execute activation command for subscription {}: {}", saved.getId(), e.getMessage());
        }

        return saved;
    }

    /**
     * Renews an existing subscription by extending its end date.
     */
    public Subscription renewSubscription(Long userId, String paymentMethod) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ContentHubException("No subscription found for user"));

        LocalDate newEnd = subscription.getEndDate().isBefore(LocalDate.now())
                ? LocalDate.now()
                : subscription.getEndDate();

        if (subscription.getPlanType().equals("YEARLY")) {
            newEnd = newEnd.plusYears(1);
        } else {
            newEnd = newEnd.plusMonths(1);
        }

        subscription.setEndDate(newEnd);
        subscription.setActive(true);
        subscription.setPaymentStatus("PAID");
        subscription.setPrice(calculateSubscriptionPrice(subscription.getPlanType()));
        subscription.setRenewedAt(LocalDateTime.now());
        Subscription renewed = subscriptionRepository.save(subscription);

        recordPayment(userId, renewed.getId(), renewed.getPrice(), paymentMethod);

        // COMMAND PATTERN: execute renewal command
        try {
            commandInvoker.invoke(new RenewSubscriptionCommand(renewed));
        } catch (Exception e) {
            // Log the error but don't fail the renewal
            logger.warn("Failed to execute renewal command for subscription {}: {}", renewed.getId(), e.getMessage());
        }
        return renewed;
    }

    public Optional<Subscription> getSubscriptionByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    /**
     * Scheduled job: auto-expire subscriptions past their end date.
     * Runs every day at midnight.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void expireOldSubscriptions() {
        List<Subscription> expired = subscriptionRepository
                .findExpiredActiveSubscriptions(LocalDate.now());

        expired.forEach(sub -> {
            sub.setActive(false);
            subscriptionRepository.save(sub);
            // COMMAND PATTERN: execute expiry command
            commandInvoker.invoke(new ExpireSubscriptionCommand(sub));
        });
    }

    /**
     * Returns plan info resolved via Chain of Responsibility for display in UI.
     */
    public SubscriptionPlan getPlanDetails(String planType) {
        return PlanHandlerChain.fromPlanType(planType);
    }

    private double calculateSubscriptionPrice(String planType) {
        if (planType == null) {
            return 0.0;
        }
        String normalized = planType.toUpperCase();
        return switch (normalized) {
            case "YEARLY" -> Math.round(PlanHandlerChain.fromPlanType(normalized).getMonthlyPrice() * 12 * 83);
            case "MONTHLY" -> Math.round(PlanHandlerChain.fromPlanType(normalized).getMonthlyPrice() * 83);
            case "FREE", "BASIC" -> 0.0;
            default -> 0.0;
        };
    }

    private void recordPayment(Long userId, Long subscriptionId, double amount, String method) {
        Payment payment = Payment.builder()
                .userId(userId)
                .subscriptionId(subscriptionId)
                .amount(amount)
                .paymentMethod(method != null ? method : "CARD")
                .status("SUCCESS")
                .transactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();
        paymentRepository.save(payment);
    }
}
