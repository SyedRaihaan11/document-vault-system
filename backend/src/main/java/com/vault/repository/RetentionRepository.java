package com.vault.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vault.entity.RetentionPolicy;

public interface RetentionRepository extends JpaRepository<RetentionPolicy, Long> {

    List<RetentionPolicy> findByActiveTrue();

    boolean existsByNameIgnoreCase(String name);
}