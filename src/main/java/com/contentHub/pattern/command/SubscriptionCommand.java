package com.contentHub.pattern.command;

/**
 * =====================================================
 * DESIGN PATTERN 4: COMMAND PATTERN (Behavioral)
 * =====================================================
 *
 * Intent: Encapsulate a request as an object, thereby letting you
 * parameterise clients with different requests, queue or log requests,
 * and support undoable operations.
 *
 * In this system:
 * - Command Interface:   SubscriptionCommand  ← this file
 * - Concrete Commands:   ActivateSubscriptionCommand, RenewSubscriptionCommand,
 *                        ExpireSubscriptionCommand
 * - Invoker:             SubscriptionCommandInvoker
 *
 * How it maps to our domain:
 * Each subscription lifecycle event (activate, renew, expire) is wrapped
 * in a command object. The invoker executes and logs them, making it easy
 * to audit, queue, or replay subscription actions.
 */
public interface SubscriptionCommand {
    /** Execute the subscription action encapsulated by this command. */
    void execute();
    /** Human-readable description of this command for logging/audit. */
    String getDescription();
}
