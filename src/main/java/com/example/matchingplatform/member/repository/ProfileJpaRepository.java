package com.example.matchingplatform.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.matchingplatform.member.repository.entity.Category;
import com.example.matchingplatform.member.repository.entity.ProfileEntity;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, Long> {
    @Query("select p from ProfileEntity p where p.memberEntity.email = :email and p.categoryEntity.category = :category")
    Optional<ProfileEntity> findByEmailAndCategory(String email, Category category);
}
