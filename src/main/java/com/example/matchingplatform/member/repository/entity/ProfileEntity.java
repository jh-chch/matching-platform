package com.example.matchingplatform.member.repository.entity;

import org.springframework.util.Assert;

import com.example.matchingplatform.common.BaseDateTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profile")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileEntity extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private Long viewCount;

    @Builder
    public ProfileEntity(MemberEntity memberEntity, CategoryEntity categoryEntity, String description, Long viewCount) {
        Assert.notNull(memberEntity, "memberEntity는 필수입니다.");
        Assert.notNull(categoryEntity, "categoryEntity는 필수입니다.");
        Assert.hasText(description, "description은 필수입니다.");

        this.memberEntity = memberEntity;
        this.categoryEntity = categoryEntity;
        this.description = description;
        this.viewCount = viewCount != null ? viewCount : 0L;
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }
}
