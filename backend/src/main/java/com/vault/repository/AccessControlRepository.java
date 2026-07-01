package com.vault.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vault.entity.AccessControl;
import com.vault.entity.Document;
import com.vault.entity.Folder;
import com.vault.entity.User;

public interface AccessControlRepository extends JpaRepository<AccessControl, Long> {

    List<AccessControl> findByDocument(Document document);

    List<AccessControl> findByFolder(Folder folder);

    // All grants made TO a given user (their "shared with me" view)
    List<AccessControl> findByUser(User user);

    Optional<AccessControl> findByDocumentAndUser(Document document, User user);

    Optional<AccessControl> findByFolderAndUser(Folder folder, User user);

    void deleteByDocumentAndUser(Document document, User user);

    void deleteByFolderAndUser(Folder folder, User user);
}