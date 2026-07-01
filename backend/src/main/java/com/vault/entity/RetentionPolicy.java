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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A reusable, named compliance policy. Documents and Folders optionally
 * reference one via a nullable FK; a scheduled job (RetentionService)
 * evaluates createdAt + retentionPeriodDays against now() and applies
 * the configured action.
 */
@Entity
@Table(name = "retention_policies")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RetentionPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // e.g. "7-Year Financial Records"

    @Column(length = 255)
    private String description;

    @Column(name = "retention_period_days", nullable = false)
    private Integer retentionPeriodDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RetentionAction action; // ARCHIVE or DELETE once the period elapses

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum RetentionAction {
        ARCHIVE, DELETE
    }
}