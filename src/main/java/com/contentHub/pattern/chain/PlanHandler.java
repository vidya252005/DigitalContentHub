package com.contentHub.pattern.chain;

/**
 * Abstract handler in the Chain of Responsibility.
 * Each concrete handler either handles the plan type or delegates to the next.
 */
public abstract class PlanHandler {

    protected PlanHandler next;

    /** Set the next handler in the chain. Returns this for fluent chaining. */
    public PlanHandler setNext(PlanHandler next) {
        this.next = next;
        return this;
    }

    /**
     * Build and return the SubscriptionPlan for the given planType.
     * If this handler cannot handle it, delegate to next.
     */
    public abstract SubscriptionPlan handle(String planType);
}
