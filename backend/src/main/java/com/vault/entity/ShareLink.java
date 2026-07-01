package com.vault.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Public external sharing: a tokenized URL that grants access to a Document
 * without requiring the recipient to have a User account. Distinct from
 * AccessControl, which is for sharing with other registered users.
 */
@Entity
@Table(name = "share_links")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ShareLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(nullable = false, unique = true, length = 64)
    private String token; // random URL-safe token, e.g. UUID or secure random string

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private LinkPermission permission = LinkPermission.VIEW;

    @Column(name = "password_hash")
    private String passwordHash; // nullable: optional password protection, BCrypt hash

    @Column(name = "max_access_count")
    private Integer maxAccessCount; // nullable = unlimited

    @Column(name = "access_count", nullable = false)
    @Builder.Default
    private Integer accessCount = 0;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // nullable = no expiry

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true; // allows manual revocation

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum LinkPermission {
        VIEW, DOWNLOAD
    }
}