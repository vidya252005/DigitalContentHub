package com.contentHub.pattern.chain;

/**
 * Handles the FREE / free plan type.
 */
public class BasicPlanHandler extends PlanHandler {

    @Override
    public SubscriptionPlan handle(String planType) {
        if ("FREE".equalsIgnoreCase(planType) || planType == null || planType.isBlank()) {
            return new SubscriptionPlan() {
                public String getPlanName()              { return "Free"; }
                public double getMonthlyPrice()          { return 0.0; }
                public String getFeatureList()           { return "Access to free content; SD streaming; 1 device"; }
                public boolean supportsHDStreaming()     { return false; }
                public boolean supportsOfflineDownload() { return false; }
                public int getMaxDevices()               { return 1; }
            };
        }
        if (next != null) return next.handle(planType);
        // Fallback – return free if chain exhausted
        return handle("FREE");
    }
}
