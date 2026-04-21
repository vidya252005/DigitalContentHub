package com.contentHub.enums;

/**
 * Defines the roles supported in the Digital Content Hub.
 * Used for Role-Based Access Control (RBAC).
 */
public enum UserRole {
    SUBSCRIBER,   // Can browse, stream, manage playlists
    CURATOR,      // Can add/manage content metadata
    ADMIN         // Full system control
}
