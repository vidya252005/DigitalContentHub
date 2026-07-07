# Subscription-Based Digital Content Hub

A full-stack digital content management platform that enables users to securely access premium digital media through subscription-based authentication. The platform supports role-based access control, content management, subscription billing, playlist creation, and administrative analytics while following Object-Oriented Analysis and Design principles.

---

## Overview

The rapid growth of digital entertainment platforms has increased the need for systems that can efficiently manage, distribute, and secure access to digital content such as movies, music, and e-books.

This project provides a centralized platform that allows subscribers to access premium content based on their active subscription plans while giving content curators and administrators dedicated tools for content management and platform monitoring.

The system emphasizes scalability, maintainability, and security by implementing MVC architecture along with multiple software design principles and design patterns.

---
# Screenshots

The application includes the following interfaces:

* Landing Page
<img width="644" height="366" alt="Screenshot 2026-07-08 at 00 30 06" src="https://github.com/user-attachments/assets/fab661b8-6e9b-4a5f-9635-d472855e6d15" />

* Registration Page
<img width="627" height="332" alt="Screenshot 2026-07-08 at 00 30 55" src="https://github.com/user-attachments/assets/e918cf62-f431-42be-90ef-c4b4c68cc8b9" />

* Login Page
<img width="631" height="334" alt="Screenshot 2026-07-08 at 00 31 25" src="https://github.com/user-attachments/assets/367d304f-8012-4f6f-b990-cc45fe861af5" />

* User Dashboard
<img width="634" height="355" alt="Screenshot 2026-07-08 at 00 31 49" src="https://github.com/user-attachments/assets/6a146497-276d-40a2-9d87-bcc337f15dbe" />

* Subscription Plans
<img width="643" height="363" alt="Screenshot 2026-07-08 at 00 32 06" src="https://github.com/user-attachments/assets/92c5fd83-5bb9-4fba-8a98-bf64f3d0d69c" />

<img width="641" height="360" alt="Screenshot 2026-07-08 at 00 32 21" src="https://github.com/user-attachments/assets/192b24a2-3c62-4119-b060-01c2f72289b1" />

 <img width="629" height="349" alt="Screenshot 2026-07-08 at 00 33 18" src="https://github.com/user-attachments/assets/063debbe-1e1c-448e-9b18-96cffcf3cead" />
<img width="638" height="342" alt="Screenshot 2026-07-08 at 00 33 43" src="https://github.com/user-attachments/assets/0baf6a7e-f285-40e1-9c21-4413d159b8bc" />

* Browse Content
<img width="640" height="370" alt="Screenshot 2026-07-08 at 00 34 32" src="https://github.com/user-attachments/assets/b1317db6-5587-4a8e-b5b5-c438ff23778d" />


* Playlist Management
<img width="630" height="341" alt="Screenshot 2026-07-08 at 00 34 45" src="https://github.com/user-attachments/assets/5a09dd90-9ed1-409b-a4c7-89ff735f797d" />

* Admin Dashboard
<img width="632" height="345" alt="Screenshot 2026-07-08 at 00 36 39" src="https://github.com/user-attachments/assets/b0d7fb11-9888-42dd-96f9-bfaaa3a65c48" />



* Content Management

<img width="630" height="333" alt="Screenshot 2026-07-08 at 00 35 45" src="https://github.com/user-attachments/assets/e7510d95-4c7f-43f0-ae06-d6ca00958544" />
<img width="632" height="351" alt="Screenshot 2026-07-08 at 00 35 05" src="https://github.com/user-attachments/assets/5cb45e13-aa1d-4ead-97f8-fea5e8c75248" />
<img width="611" height="341" alt="Screenshot 2026-07-08 at 00 35 29" src="https://github.com/user-attachments/assets/f0baa705-d920-46c6-9b13-fd79673f1849" />

* Upload Content
<img width="632" height="345" alt="Screenshot 2026-07-08 at 00 37 17" src="https://github.com/user-attachments/assets/c328e5c7-8703-47c7-9598-9ec1785e35a1" />
<img width="634" height="345" alt="Screenshot 2026-07-08 at 00 37 06" src="https://github.com/user-attachments/assets/1dd7d0df-b2e0-41ac-831c-623a7e86a67a" />

* Subscriber Management
<img width="642" height="320" alt="Screenshot 2026-07-08 at 00 37 29" src="https://github.com/user-attachments/assets/8e2cc7cb-e635-494d-bffd-5b89b2749bbf" />


## Features

### Multi-Role User Management

* User Registration and Login
* Role-Based Access Control
* Subscriber Dashboard
* Content Curator Dashboard
* Administrator Dashboard

---


### Content Catalog

* Browse Movies, Music, and E-books
* Search and Filter Content
* Content Metadata

  * Genre
  * Creator
  * Ratings
  * Popularity
* Trending Content

---


### Subscription Management

* Monthly Subscription Plans
* Yearly Subscription Plans
* Active Subscription Verification
* Subscription Renewal
* Automatic Subscription Expiry
* Payment Status Tracking
* Billing History

---

### Secure Premium Content Access

* Subscription Validation before Access
* Protected Streaming
* Secure Downloads
* Unauthorized Access Prevention

---

### Personal Libraries and Playlists
* Create Playlists
* Edit Playlists
* Delete Playlists
* Add Content to Playlist
* Remove Content
* Private Libraries
* Shared Collaborative Playlists

---

### Content Management

Content Curators can:

* Upload New Content
* Update Content Metadata
* Categorize Digital Assets
* Manage Availability
* Track Trending Content

---

### Administrative Features

Administrators can:

* Manage Users
* Assign Roles
* Approve Content Uploads
* Monitor Platform Activity
* View Subscription Statistics
* Generate Revenue Reports
* Analyze User Engagement

---

## System Architecture

The application follows the **Model-View-Controller (MVC)** architecture.

### Model

Represents the core business entities:

* User
* Subscription
* Content
* Payment
* Playlist

### View

Frontend pages including:

* Landing Page
* Login
* Registration
* Dashboard
* Content Catalog
* Subscription Plans
* Playlist Management
* Admin Dashboard

### Controller

Responsible for processing requests and coordinating between views and business logic.

Examples include:

* Authentication Controller
* Content Controller
* Subscription Controller
* Playlist Controller
* Admin Controller

---

# Design Principles

## Single Responsibility Principle (SRP)

Each class has a single well-defined responsibility.

Examples:

* PlaylistService handles playlist operations.
* AdminService manages administrative functionality.
* GlobalExceptionHandler handles application exceptions.

---

## Open/Closed Principle (OCP)

The system is open for extension without modifying existing code.

Example:

New subscription plans can be introduced by implementing new plan handlers without changing existing subscription logic.

---

## Dependency Inversion Principle (DIP)

High-level modules depend on abstractions rather than concrete implementations.

Examples:

* Services depend on repository interfaces.
* Dependency Injection is used throughout the application.

---

## Encapsulation

Business logic is encapsulated within domain models.

Examples:

* Subscription validation
* Expiry checks
* Payment state management

---

## Liskov Substitution Principle (LSP)

Subscription commands implement a common interface and can be used interchangeably.

Examples:

* Activate Subscription
* Renew Subscription
* Expire Subscription

---

# Design Patterns

## Proxy Pattern

Used for secure premium content access.

The proxy verifies:

* Active Subscription
* User Permissions
* Access Rights

before forwarding requests to the actual content provider.

---

## Factory Pattern

Centralizes object creation for users.

Benefits:

* Consistent initialization
* Reduced object creation complexity
* Improved maintainability

---

## Chain of Responsibility Pattern

Used for subscription plan handling.

Example flow:

```
Basic Plan
      ↓
Monthly Plan
      ↓
Yearly Plan
```

Each handler processes requests only if it is responsible for that plan.

---

## Command Pattern

Encapsulates subscription-related operations.

Commands include:

* Activate Subscription
* Renew Subscription
* Expire Subscription

Executed through a dedicated command invoker.

---

# Project Modules

* User Authentication
* User Registration
* Subscription Management
* Payment Processing
* Content Browsing
* Premium Content Streaming
* Playlist Management
* Admin Dashboard
* Content Management
* Analytics & Reporting

---

# Technology Stack

### Backend

* Java
* Spring Boot
* Spring MVC

### Frontend

* HTML
* CSS
* JavaScript
* Thymeleaf

### Database

* MySQL

### Build Tool

* Maven

---

# Project Structure

```
DigitalContentHub/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   ├── static/
│   │   │   └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── .gitignore
```


---

# Key Highlights

* Secure Subscription-Based Content Access
* Role-Based Authorization
* MVC Architecture
* Object-Oriented Design
* Multiple Design Patterns
* Modular Service Layer
* Scalable Architecture
* Centralized Exception Handling
* Premium Content Protection using Proxy Pattern
* Flexible Subscription Management
* Playlist Collaboration
* Administrative Analytics


---

## License

This project was developed as part of the **Object Oriented Analysis & Design (UE23CS352B)** course at **PES University** for academic purposes.
