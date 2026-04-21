package com.contentHub.repository;

import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import com.contentHub.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    // User-facing – only available content
    List<Content> findByAvailableTrue();
    List<Content> findByContentTypeAndAvailableTrue(ContentType contentType);
    List<Content> findByRequiredPlanAndAvailableTrue(RequiredPlan plan);
    List<Content> findByCreatedBy(Long createdBy);

    // Admin – all content regardless of availability
    List<Content> findAllByOrderByIdDesc();

    @Query("SELECT c FROM Content c WHERE c.available = true ORDER BY c.viewCount DESC")
    List<Content> findTopByViewCount();

    @Query("SELECT c FROM Content c WHERE " +
           "(LOWER(c.title) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(c.creator) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(c.genre) LIKE LOWER(CONCAT('%',:query,'%'))) " +
           "AND c.available = true")
    List<Content> searchContent(String query);

    long countByContentType(ContentType contentType);
    long countByRequiredPlan(RequiredPlan plan);
}
