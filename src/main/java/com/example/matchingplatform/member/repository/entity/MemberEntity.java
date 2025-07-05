package com.example.matchingplatform.member.repository.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.example.matchingplatform.common.BaseDateTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileEntity> profileEntities = new ArrayList<>();

    @Builder
    public MemberEntity(String name, String email) {
        Assert.notNull(name, "name은 필수입니다.");
        Assert.notNull(email, "email은 필수입니다.");
        this.name = name;
        this.email = email;
    }
}
