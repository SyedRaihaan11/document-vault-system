package com.vault.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vault.entity.Document;
import com.vault.entity.ShareLink;

public interface ShareLinkRepository extends JpaRepository<ShareLink, Long> {

    Optional<ShareLink> findByToken(String token);

    List<ShareLink> findByDocument(Document document);

    // Active, non-expired link lookup for the public access endpoint
    @Query("SELECT s FROM ShareLink s WHERE s.token = :token AND s.active = true " +
           "AND (s.expiresAt IS NULL OR s.expiresAt > :now)")
    Optional<ShareLink> findValidByToken(String token, LocalDateTime now);

    // Used by a scheduled cleanup job
    List<ShareLink> findByExpiresAtBeforeAndActiveTrue(LocalDateTime cutoff);
}