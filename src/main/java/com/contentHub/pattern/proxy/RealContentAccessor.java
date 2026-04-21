package com.contentHub.pattern.proxy;

import com.contentHub.model.Content;
import com.contentHub.model.User;
import org.springframework.stereotype.Component;

/**
 * Real Subject in the Proxy Pattern.
 * Performs the actual content access/streaming logic.
 * This is only invoked after the Proxy has verified access rights.
 */
@Component
public class RealContentAccessor implements ContentAccessor {

    @Override
    public AccessResult accessContent(User user, Content content) {
        // In a real system, this would initiate streaming, generate signed URLs, etc.
        content.incrementViewCount();
        String message = String.format(
            "Access granted. Now streaming: '%s' [%s] for user: %s",
            content.getTitle(), content.getContentType(), user.getUsername()
        );
        return new AccessResult(true, message, content.getFileUrl());
    }
}
