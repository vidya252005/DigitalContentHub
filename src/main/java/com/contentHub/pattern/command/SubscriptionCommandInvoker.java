package com.contentHub.pattern.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoker in the Command Pattern.
 *
 * Receives command objects, executes them, and maintains a history log.
 * Services call this invoker instead of directly performing side-effects,
 * keeping business logic decoupled from notification/access-control concerns.
 *
 * The command history enables auditing of all subscription lifecycle actions.
 */
@Component
public class SubscriptionCommandInvoker {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionCommandInvoker.class);
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Execute a command and record it in history.
     */
    public void invoke(SubscriptionCommand command) {
        logger.info("[INVOKER] Executing: {}", command.getDescription());
        command.execute();
        commandHistory.add(command.getDescription());
    }

    /** Returns a copy of the command execution history (for auditing). */
    public List<String> getCommandHistory() {
        return List.copyOf(commandHistory);
    }
}
