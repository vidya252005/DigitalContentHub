package com.contentHub.pattern.proxy;

import com.contentHub.enums.RequiredPlan;
import com.contentHub.model.Content;
import com.contentHub.model.Subscription;
import com.contentHub.model.User;
import com.contentHub.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PROXY PATTERN – ContentAccessProxy (Structural)
 *
 * Checks whether the user's subscription tier satisfies the
 * content's requiredPlan before delegating to RealContentAccessor.
 *
 * Access rules:
 *   FREE content    → all authenticated users
 *   MONTHLY content → requires MONTHLY or YEARLY subscription
 *   YEARLY content  → requires YEARLY subscription only
 *   ADMIN/CURATOR   → always granted (preview/moderation)
 */
@Component
public class ContentAccessProxy implements ContentAccessor {

    private static final Logger logger = LoggerFactory.getLogger(ContentAccessProxy.class);

    @Autowired
    private RealContentAccessor realContentAccessor;

    @Override
    public AccessResult accessContent(User user, Content content) {
        logger.info("[PROXY] '{}' requesting '{}'", user.getUsername(), content.getTitle());

        // Admin/Curator bypass
        if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.CURATOR) {
            return realContentAccessor.accessContent(user, content);
        }

        RequiredPlan required = content.getRequiredPlan();

        // Free content – always accessible
        if (required == RequiredPlan.FREE) {
            return realContentAccessor.accessContent(user, content);
        }

        // Need a subscription from here
        Subscription sub = user.getSubscription();
        if (sub == null || !sub.isValid()) {
            return new AccessResult(false,
                "This content requires a subscription. Please subscribe to continue.",
                null);
        }

        String userPlan = sub.getPlanType().toUpperCase();

        // MONTHLY content: MONTHLY or YEARLY both work
        if (required == RequiredPlan.MONTHLY) {
            if (userPlan.equals("MONTHLY") || userPlan.equals("YEARLY")) {
                return realContentAccessor.accessContent(user, content);
            }
            return new AccessResult(false,
                "This content requires a Monthly or Yearly plan. Upgrade to continue.", null);
        }

        // YEARLY content: only YEARLY
        if (required == RequiredPlan.YEARLY) {
            if (userPlan.equals("YEARLY")) {
                return realContentAccessor.accessContent(user, content);
            }
            return new AccessResult(false,
                "This is exclusive Yearly-plan content. Upgrade to Yearly to access it.", null);
        }

        return new AccessResult(false, "Access denied.", null);
    }
}
