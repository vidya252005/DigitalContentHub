package com.contentHub.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Catches unhandled exceptions and routes them to an error view.
 * Design Principle: Single Responsibility – error handling isolated here.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ContentHubException.class)
    public String handleContentHubException(ContentHubException ex, Model model) {
        logger.warn("Business exception: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        logger.error("Unexpected error", ex);
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        return "error";
    }
}
