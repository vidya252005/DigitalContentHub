package com.contentHub.pattern.chain;

/**
 * Handles the YEARLY subscription plan type.
 */
public class YearlyPlanHandler extends PlanHandler {

    @Override
    public SubscriptionPlan handle(String planType) {
        if ("YEARLY".equalsIgnoreCase(planType)) {
            return new SubscriptionPlan() {
                public String getPlanName()              { return "Yearly Premium (20% off)"; }
                public double getMonthlyPrice()          { return 7.99; }
                public String getFeatureList()           { return "All content; HD Streaming; Offline Downloads; 3 devices; 20% yearly discount"; }
                public boolean supportsHDStreaming()     { return true; }
                public boolean supportsOfflineDownload() { return true; }
                public int getMaxDevices()               { return 3; }
            };
        }
        if (next != null) return next.handle(planType);
        return new BasicPlanHandler().handle("FREE");
    }
}
