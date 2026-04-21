package com.contentHub.service;

import com.contentHub.dto.ContentDTO;
import com.contentHub.enums.ContentType;
import com.contentHub.enums.RequiredPlan;
import com.contentHub.exception.ContentHubException;
import com.contentHub.model.Content;
import com.contentHub.model.User;
import com.contentHub.pattern.proxy.AccessResult;
import com.contentHub.pattern.proxy.ContentAccessProxy;
import com.contentHub.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContentService {

    @Autowired private ContentRepository contentRepository;
    @Autowired private ContentAccessProxy contentAccessProxy;

    public List<Content> getAllAvailableContent() {
        return contentRepository.findByAvailableTrue();
    }

    /** Admin view – all content including hidden */
    public List<Content> getAllContentForAdmin() {
        return contentRepository.findAllByOrderByIdDesc();
    }

    public List<Content> getContentByType(ContentType type) {
        return contentRepository.findByContentTypeAndAvailableTrue(type);
    }

    public List<Content> getContentByRequiredPlan(RequiredPlan plan) {
        return contentRepository.findByRequiredPlanAndAvailableTrue(plan);
    }

    public List<Content> searchContent(String query) {
        if (query == null || query.isBlank()) return getAllAvailableContent();
        return contentRepository.searchContent(query.trim());
    }

    public List<Content> getTrendingContent() {
        return contentRepository.findTopByViewCount();
    }

    public Content getContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ContentHubException("Content not found: " + id));
    }

    public AccessResult accessContent(User user, Long contentId) {
        Content content = getContentById(contentId);
        return contentAccessProxy.accessContent(user, content);
    }

    public Content addContent(ContentDTO dto, Long curatorId) {
        Content content = Content.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .contentType(dto.getContentType())
                .genre(dto.getGenre())
                .creator(dto.getCreator())
                .fileUrl(dto.getFileUrl() != null ? dto.getFileUrl() : "/content/files/placeholder")
                .thumbnailUrl(dto.getThumbnailUrl() != null ? dto.getThumbnailUrl() : "/content/thumbs/default.jpg")
                .requiredPlan(dto.getRequiredPlan() != null ? dto.getRequiredPlan() : RequiredPlan.FREE)
                .createdBy(curatorId)
                .available(true)
                .build();
        return contentRepository.save(content);
    }

    public Content updateContent(Long id, ContentDTO dto) {
        Content content = getContentById(id);
        content.setTitle(dto.getTitle());
        content.setDescription(dto.getDescription());
        content.setGenre(dto.getGenre());
        content.setCreator(dto.getCreator());
        if (dto.getContentType() != null) content.setContentType(dto.getContentType());
        content.setRequiredPlan(dto.getRequiredPlan() != null ? dto.getRequiredPlan() : RequiredPlan.FREE);
        content.setAvailable(dto.isAvailable());
        return contentRepository.save(content);
    }

    public void removeContent(Long id) {
        Content content = getContentById(id);
        content.setAvailable(false);
        contentRepository.save(content);
    }

    public void hardDeleteContent(Long id) {
        contentRepository.deleteById(id);
    }
}
