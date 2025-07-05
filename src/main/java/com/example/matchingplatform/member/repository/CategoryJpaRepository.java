package com.example.matchingplatform.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.matchingplatform.member.repository.entity.Category;
import com.example.matchingplatform.member.repository.entity.CategoryEntity;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategory(Category category);
} 