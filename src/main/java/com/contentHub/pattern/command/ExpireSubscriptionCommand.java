package com.contentHub.pattern.command;

import com.contentHub.model.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete Command: handles subscription expiry side-effects.
 */
public class ExpireSubscriptionCommand implements SubscriptionCommand {

    private static final Logger logger = LoggerFactory.getLogger(ExpireSubscriptionCommand.class);
    private final Subscription subscription;

    public ExpireSubscriptionCommand(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void execute() {
        try {
            String email = subscription.getUser().getEmail();
            logger.info("[COMMAND] Expiry \u2013 sending notice to {} | Expired on: {}",
                    email, subscription.getEndDate());
            // Production: emailService.sendExpiryNotice(email, subscription.getEndDate());
            // Production: accessService.revokePlanAccess(subscription.getUser().getId());
        } catch (Exception e) {
            logger.warn("[COMMAND] Expiry notification failed for subscription {}: {}", subscription.getId(), e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "ExpireSubscription[userId=" + subscription.getUser().getId()
                + ", expiredOn=" + subscription.getEndDate() + "]";
    }
}
