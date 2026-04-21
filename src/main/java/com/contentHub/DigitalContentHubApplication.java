package com.contentHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Subscription-Based Digital Content Hub.
 *
 * UE23CS352B – Object Oriented Analysis & Design
 * Mini Project – Digital Content Management Platform
 *
 * Architecture: MVC (Model-View-Controller) via Spring MVC
 * Design Patterns Used:
 *   1. Proxy Pattern                – ContentAccessProxy (Structural)
 *   2. Factory Pattern              – UserFactory (Creational)
 *   3. Chain of Responsibility      – PlanHandlerChain (Behavioral)
 *   4. Command Pattern              – SubscriptionCommandInvoker (Behavioral)
 */
@SpringBootApplication
@EnableScheduling
public class DigitalContentHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalContentHubApplication.class, args);
        System.out.println("""
                
                ╔══════════════════════════════════════════════════════╗
                ║     Subscription-Based Digital Content Hub           ║
                ║     UE23CS352B - OOAD Mini Project                   ║
                ╠══════════════════════════════════════════════════════╣
                ║  App running at: http://localhost:8080               ║
                ║  H2 Console:     http://localhost:8080/h2-console    ║
                ║  Default Admin:  admin / password123                 ║
                ║  Default User:   user1 / password123                 ║
                ╚══════════════════════════════════════════════════════╝
                """);
    }
}
