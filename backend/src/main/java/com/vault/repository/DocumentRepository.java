package com.vault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vault.entity.Document;
import com.vault.entity.Folder;
import com.vault.entity.User;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByOwner(User owner);

    List<Document> findByFolder(Folder folder);

    // Root-level documents for a user (not inside any folder)
    List<Document> findByOwnerAndFolderIsNull(User owner);

    // Used by SearchService — title search scoped to the requesting user
    List<Document> findByOwnerAndTitleContainingIgnoreCase(User owner, String title);

    // Used by SearchService for tag-based filtering
    @Query("SELECT d FROM Document d JOIN d.tags t WHERE d.owner = :owner AND t.name = :tagName")
    List<Document> findByOwnerAndTagName(@Param("owner") User owner, @Param("tagName") String tagName);

    // Documents bound to a retention policy, used by the retention sweep job
    List<Document> findByRetentionPolicyId(Long retentionPolicyId);
}