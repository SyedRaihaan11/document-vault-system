package com.vault.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vault.entity.Document;
import com.vault.entity.DocumentVersion;

public interface VersionRepository extends JpaRepository<DocumentVersion, Long> {

    List<DocumentVersion> findByDocumentOrderByVersionNumberDesc(Document document);

    Optional<DocumentVersion> findByDocumentAndVersionNumber(Document document, Integer versionNumber);

    // Used by VersionService to compute the next version number
    Optional<DocumentVersion> findTopByDocumentOrderByVersionNumberDesc(Document document);
}