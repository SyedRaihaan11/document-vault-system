package com.vault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vault.entity.Folder;
import com.vault.entity.User;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByOwner(User owner);

    // Root-level folders for a user (no parent)
    List<Folder> findByOwnerAndParentFolderIsNull(User owner);

    // Direct children of a given folder
    List<Folder> findByParentFolder(Folder parentFolder);

    boolean existsByOwnerAndNameAndParentFolder(User owner, String name, Folder parentFolder);
}