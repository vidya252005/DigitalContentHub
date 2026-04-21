package com.contentHub.enums;

/**
 * Defines which subscription tier is required to access a piece of content.
 *
 * FREE    – free content, accessible to all authenticated users
 * MONTHLY – requires at least a monthly (or yearly) subscription
 * YEARLY  – exclusive content, requires a yearly subscription only
 */
public enum RequiredPlan {
    FREE,
    MONTHLY,
    YEARLY
}
