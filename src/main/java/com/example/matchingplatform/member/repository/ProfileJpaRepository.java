package com.example.matchingplatform.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.matchingplatform.member.repository.entity.ProfileEntity;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, Long> {
}
