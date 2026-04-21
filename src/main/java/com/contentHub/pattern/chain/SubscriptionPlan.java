package com.contentHub.pattern.chain;

/**
 * =====================================================
 * DESIGN PATTERN 3: CHAIN OF RESPONSIBILITY (Behavioral)
 * =====================================================
 *
 * Intent: Pass a request along a chain of handlers. Each handler decides
 * either to process the request or to pass it to the next handler.
 *
 * In this system:
 * - Handler Interface:   SubscriptionPlan  ← this file
 * - Concrete Handlers:   BasicPlanHandler, MonthlyPlanHandler, YearlyPlanHandler
 * - Client:             PlanHandlerChain
 *
 * How it maps to our domain:
 * When building plan features, a request travels through a chain of plan
 * handlers. Each handler checks if it can handle the given plan type and
 * either enriches the plan details or passes it to the next handler.
 * This replaces a rigid if-else/decorator tree with a flexible chain.
 */
public interface SubscriptionPlan {
    String getPlanName();
    double getMonthlyPrice();
    String getFeatureList();
    boolean supportsHDStreaming();
    boolean supportsOfflineDownload();
    int getMaxDevices();
}
