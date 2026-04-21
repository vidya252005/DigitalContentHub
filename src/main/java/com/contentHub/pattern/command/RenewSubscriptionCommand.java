package com.contentHub.pattern.command;

import com.contentHub.model.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Concrete Command: handles subscription renewal side-effects.
 */
public class RenewSubscriptionCommand implements SubscriptionCommand {

    private static final Logger logger = LoggerFactory.getLogger(RenewSubscriptionCommand.class);
    private final Subscription subscription;

    public RenewSubscriptionCommand(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void execute() {
        try {
            String email = subscription.getUser().getEmail();
            logger.info("[COMMAND] Renewal \u2013 sending confirmation to {} | New expiry: {}",
                    email, subscription.getEndDate());
            // Production: emailService.sendRenewalConfirmation(email, subscription.getEndDate());
        } catch (Exception e) {
            logger.warn("[COMMAND] Renewal notification failed for subscription {}: {}", subscription.getId(), e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "RenewSubscription[userId=" + subscription.getUser().getId()
                + ", newEndDate=" + subscription.getEndDate() + "]";
    }
}
