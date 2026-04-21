package com.contentHub.pattern.proxy;

import com.contentHub.model.Content;
import com.contentHub.model.User;

/**
 * =====================================================
 * DESIGN PATTERN 1: PROXY PATTERN (Structural)
 * =====================================================
 *
 * Intent: Provide a surrogate/placeholder for the real content object,
 * controlling access based on user subscription status.
 *
 * In this system:
 * - Subject Interface: ContentAccessor
 * - Real Subject:      RealContentAccessor  (actual content streamer)
 * - Proxy:             ContentAccessProxy   (subscription verifier)
 *
 * How it maps to our domain:
 * Before any premium content is streamed or downloaded, the PROXY
 * intercepts the request, validates the user's subscription plan,
 * and only delegates to the RealContentAccessor if authorized.
 * This prevents unauthorized access and reduces server load.
 */
public interface ContentAccessor {

    /**
     * Stream/access a piece of content.
     *
     * @param user    The requesting user
     * @param content The content to be accessed
     * @return AccessResult describing success/failure and a message
     */
    AccessResult accessContent(User user, Content content);
}
