package com.contentHub.repository;

import com.contentHub.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Layer for Subscription entity.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserId(Long userId);

    List<Subscription> findByActiveTrue();

    List<Subscription> findByEndDateBefore(LocalDate date);

    @Query("SELECT s FROM Subscription s WHERE s.active = true AND s.endDate < :today")
    List<Subscription> findExpiredActiveSubscriptions(LocalDate today);

    long countByPlanType(String planType);

    @Query("SELECT COALESCE(SUM(s.price), 0) FROM Subscription s WHERE s.paymentStatus = 'PAID'")
    double calculateTotalRevenue();
}
