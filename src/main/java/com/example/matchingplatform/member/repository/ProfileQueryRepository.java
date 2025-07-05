package com.example.matchingplatform.member.repository;

import static com.example.matchingplatform.member.repository.entity.QCategoryEntity.categoryEntity;
import static com.example.matchingplatform.member.repository.entity.QMemberEntity.memberEntity;
import static com.example.matchingplatform.member.repository.entity.QProfileEntity.profileEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.matchingplatform.member.controller.dto.GetProfileResponse;
import com.example.matchingplatform.member.controller.dto.ProfileSortType;
import com.example.matchingplatform.member.controller.dto.QGetProfileResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProfileQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<GetProfileResponse> findAllProfiles(ProfileSortType sortType, Pageable pageable) {
        List<GetProfileResponse> content = queryFactory
                .select(
                        new QGetProfileResponse(
                                memberEntity.email,
                                memberEntity.name,
                                categoryEntity.category.stringValue(),
                                profileEntity.description,
                                profileEntity.viewCount,
                                profileEntity.createDateTime))
                .from(profileEntity)
                .join(profileEntity.memberEntity, memberEntity)
                .join(profileEntity.categoryEntity, categoryEntity)
                .orderBy(getOrderSpecifier(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(profileEntity.count())
                .from(profileEntity)
                .from(profileEntity)
                .join(profileEntity.memberEntity, memberEntity)
                .join(profileEntity.categoryEntity, categoryEntity)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private OrderSpecifier<?> getOrderSpecifier(ProfileSortType sortType) {
        if (sortType == null)
            return profileEntity.createDateTime.desc();

        switch (sortType) {
            case NAME_ASC:
                return memberEntity.name.asc();
            case NAME_DESC:
                return memberEntity.name.desc();
            case VIEW_COUNT_ASC:
                return profileEntity.viewCount.asc();
            case VIEW_COUNT_DESC:
                return profileEntity.viewCount.desc();
            case CREATE_DATE_ASC:
                return profileEntity.createDateTime.asc();
            case CREATE_DATE_DESC:
                return profileEntity.createDateTime.desc();
            default:
                return profileEntity.createDateTime.desc();
        }
    }
}
