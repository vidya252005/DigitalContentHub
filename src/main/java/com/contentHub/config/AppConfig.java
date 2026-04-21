package com.contentHub.config;

import com.contentHub.pattern.command.SubscriptionCommandInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application-level bean wiring.
 * Logs startup confirmation that the Command Pattern invoker is ready.
 */
@Configuration
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private SubscriptionCommandInvoker commandInvoker;

    /**
     * Confirms Command Pattern invoker is wired on startup.
     * Subscription lifecycle commands (activate, renew, expire) will be
     * routed through SubscriptionCommandInvoker.
     */
    @PostConstruct
    public void onStartup() {
        logger.info("[COMMAND] SubscriptionCommandInvoker ready. History size: {}",
                commandInvoker.getCommandHistory().size());
    }
}
