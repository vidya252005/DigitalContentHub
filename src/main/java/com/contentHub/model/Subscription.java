package com.contentHub.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "plan_type", nullable = false)
    private String planType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private double price;

    @Column(name = "payment_status")
    private String paymentStatus = "PENDING";

    @Column(name = "renewed_at")
    private LocalDateTime renewedAt;

    public Subscription() {}

    public boolean isExpired() {
        return endDate != null && LocalDate.now().isAfter(endDate);
    }

    public boolean isValid() {
        return active && !isExpired() && "PAID".equals(paymentStatus);
    }

    public long getDaysRemaining() {
        if (endDate == null || isExpired()) return 0;
        if ("YEARLY".equalsIgnoreCase(planType)) {
            return 364;
        } else if ("MONTHLY".equalsIgnoreCase(planType)) {
            return 30;
        }
        return 0;
    }

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getPlanType() { return planType; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public boolean isActive() { return active; }
    public double getPrice() { return price; }
    public String getPaymentStatus() { return paymentStatus; }
    public LocalDateTime getRenewedAt() { return renewedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setPlanType(String planType) { this.planType = planType; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setActive(boolean active) { this.active = active; }
    public void setPrice(double price) { this.price = price; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setRenewedAt(LocalDateTime renewedAt) { this.renewedAt = renewedAt; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private User user; private String planType;
        private LocalDate startDate, endDate; private boolean active = true;
        private double price; private String paymentStatus = "PENDING";
        private LocalDateTime renewedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User u) { this.user = u; return this; }
        public Builder planType(String p) { this.planType = p; return this; }
        public Builder startDate(LocalDate d) { this.startDate = d; return this; }
        public Builder endDate(LocalDate d) { this.endDate = d; return this; }
        public Builder active(boolean a) { this.active = a; return this; }
        public Builder price(double p) { this.price = p; return this; }
        public Builder paymentStatus(String ps) { this.paymentStatus = ps; return this; }
        public Builder renewedAt(LocalDateTime r) { this.renewedAt = r; return this; }

        public Subscription build() {
            Subscription s = new Subscription();
            s.id = id; s.user = user; s.planType = planType;
            s.startDate = startDate; s.endDate = endDate; s.active = active;
            s.price = price; s.paymentStatus = paymentStatus; s.renewedAt = renewedAt;
            return s;
        }
    }
}
