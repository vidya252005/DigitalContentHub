package com.contentHub.pattern.chain;

import org.springframework.stereotype.Component;

/**
 * Assembles the Chain of Responsibility for plan handling.
 *
 * Chain order: BasicPlanHandler → MonthlyPlanHandler → YearlyPlanHandler
 *
 * Usage:
 *   SubscriptionPlan plan = PlanHandlerChain.resolve("MONTHLY");
 */
@Component
public class PlanHandlerChain {

    private static final PlanHandler chain;

    static {
        // Build the chain once: Free → Monthly → Yearly
        BasicPlanHandler basic   = new BasicPlanHandler();
        MonthlyPlanHandler monthly = new MonthlyPlanHandler();
        YearlyPlanHandler yearly  = new YearlyPlanHandler();
        basic.setNext(monthly).setNext(yearly);
        chain = basic;
    }

    /**
     * Resolve a plan by type string.
     * The request travels the chain until a handler claims it.
     */
    public static SubscriptionPlan resolve(String planType) {
        return chain.handle(planType);
    }

    /** Convenience alias — maps planType to a SubscriptionPlan. */
    public static SubscriptionPlan fromPlanType(String planType) {
        return resolve(planType);
    }
}
