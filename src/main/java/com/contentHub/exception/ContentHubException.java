package com.contentHub.exception;

/**
 * Application-level exception for business rule violations.
 */
public class ContentHubException extends RuntimeException {
    public ContentHubException(String message) {
        super(message);
    }
    public ContentHubException(String message, Throwable cause) {
        super(message, cause);
    }
}
