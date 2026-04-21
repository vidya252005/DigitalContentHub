package com.contentHub.dto;

import jakarta.validation.constraints.NotBlank;

public class SubscriptionDTO {

    @NotBlank(message = "Plan type is required")
    private String planType;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    public SubscriptionDTO() {}

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
