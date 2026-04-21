package com.contentHub.pattern.chain;

/**
 * Handles the MONTHLY subscription plan type.
 */
public class MonthlyPlanHandler extends PlanHandler {

    @Override
    public SubscriptionPlan handle(String planType) {
        if ("MONTHLY".equalsIgnoreCase(planType)) {
            return new SubscriptionPlan() {
                public String getPlanName()              { return "Monthly Premium"; }
                public double getMonthlyPrice()          { return 9.99; }
                public String getFeatureList()           { return "All free content; Monthly content; HD Streaming; 2 devices"; }
                public boolean supportsHDStreaming()     { return true; }
                public boolean supportsOfflineDownload() { return false; }
                public int getMaxDevices()               { return 2; }
            };
        }
        if (next != null) return next.handle(planType);
        return new BasicPlanHandler().handle("FREE");
    }
}
