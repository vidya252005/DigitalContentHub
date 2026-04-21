package com.contentHub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    @Column(nullable = false)
    private double amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    public Payment() {
        this.paymentDate = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getSubscriptionId() { return subscriptionId; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public String getTransactionId() { return transactionId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setSubscriptionId(Long subscriptionId) { this.subscriptionId = subscriptionId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setStatus(String status) { this.status = status; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id, userId, subscriptionId;
        private double amount;
        private LocalDateTime paymentDate;
        private String paymentMethod, status = "PENDING", transactionId;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(Long u) { this.userId = u; return this; }
        public Builder subscriptionId(Long s) { this.subscriptionId = s; return this; }
        public Builder amount(double a) { this.amount = a; return this; }
        public Builder paymentDate(LocalDateTime d) { this.paymentDate = d; return this; }
        public Builder paymentMethod(String m) { this.paymentMethod = m; return this; }
        public Builder status(String s) { this.status = s; return this; }
        public Builder transactionId(String t) { this.transactionId = t; return this; }

        public Payment build() {
            Payment p = new Payment();
            p.id = id; p.userId = userId; p.subscriptionId = subscriptionId;
            p.amount = amount; p.paymentDate = paymentDate != null ? paymentDate : LocalDateTime.now(); p.paymentMethod = paymentMethod;
            p.status = status; p.transactionId = transactionId;
            return p;
        }
    }
}
