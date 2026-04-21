package com.contentHub.pattern.command;

import com.contentHub.model.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete Command: handles subscription activation side-effects.
 * Simulates sending a welcome email and enabling content access.
 */
public class ActivateSubscriptionCommand implements SubscriptionCommand {

    private static final Logger logger = LoggerFactory.getLogger(ActivateSubscriptionCommand.class);
    private final Subscription subscription;

    public ActivateSubscriptionCommand(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void execute() {
        try {
            String email = subscription.getUser().getEmail();
            String plan  = subscription.getPlanType();
            logger.info("[COMMAND] Activation – sending welcome email to {} | Plan: {} | Valid until: {}",
                    email, plan, subscription.getEndDate());
            // Production: emailService.sendWelcome(email, plan, subscription.getEndDate());
            // Production: accessService.grantPlanAccess(subscription.getUser().getId(), plan);
        } catch (Exception e) {
            logger.warn("[COMMAND] Activation failed for subscription {}: {}", subscription.getId(), e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "ActivateSubscription[userId=" + subscription.getUser().getId()
                + ", plan=" + subscription.getPlanType() + "]";
    }
}
