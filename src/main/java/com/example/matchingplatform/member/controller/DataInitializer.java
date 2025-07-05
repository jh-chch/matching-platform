package com.example.matchingplatform.member.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.matchingplatform.member.repository.CategoryJpaRepository;
import com.example.matchingplatform.member.repository.MemberJpaRepository;
import com.example.matchingplatform.member.repository.ProfileJpaRepository;
import com.example.matchingplatform.member.repository.entity.Category;
import com.example.matchingplatform.member.repository.entity.CategoryEntity;
import com.example.matchingplatform.member.repository.entity.MemberEntity;
import com.example.matchingplatform.member.repository.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberJpaRepository memberJpaRepository;
    private final ProfileJpaRepository profileJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public void run(String... args) throws Exception {
        // 카테고리 초기화
        initializeCategories();

        // 테스트 회원 데이터 초기화
        initializeTestMembers();
    }

    private void initializeCategories() {
        for (Category category : Category.values()) {
            if (!categoryJpaRepository.findByCategory(category).isPresent()) {
                categoryJpaRepository.save(new CategoryEntity(category));
            }
        }
    }

    private void initializeTestMembers() {
        createTestMember("최정헌", "choi@test.com", Category.IT, "Java 개발자입니다.");
        createTestMember("홍길동", "hong@test.com", Category.ARCHITECTURE, "건축 설계사입니다.");
        createTestMember("유관순", "yu@test.com", Category.OFFICE, "행정 업무 경험 5년차입니다.");
        createTestMember("이순신", "lee@test.com", Category.IT, "프론트엔드 개발자입니다.");
        createTestMember("고길동", "ko@test.com", Category.ARCHITECTURE, "건축 전문가입니다.");
    }

    private void createTestMember(String name, String email, Category category, String description) {
        // 회원 생성
        MemberEntity memberEntity = MemberEntity.builder()
                .name(name)
                .email(email)
                .build();
        memberJpaRepository.save(memberEntity);

        // 카테고리 조회
        CategoryEntity categoryEntity = categoryJpaRepository.findByCategory(category)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다: " + category));

        // 프로필 생성
        ProfileEntity profileEntity = ProfileEntity.builder()
                .memberEntity(memberEntity)
                .categoryEntity(categoryEntity)
                .description(description)
                .viewCount(0L)
                .build();
        profileJpaRepository.save(profileEntity);
    }
}