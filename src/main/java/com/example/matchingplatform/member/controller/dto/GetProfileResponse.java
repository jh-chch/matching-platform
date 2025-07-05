package com.example.matchingplatform.member.controller.dto;

import java.time.LocalDateTime;

import com.example.matchingplatform.member.repository.entity.ProfileEntity;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetProfileResponse {
    private String email;
    private String memberName;
    private String categoryName;
    private String description;
    private Long viewCount;
    private LocalDateTime createDateTime;

    @QueryProjection
    public GetProfileResponse(String email, String memberName, String categoryName,
            String description, Long viewCount, LocalDateTime createDateTime) {
        this.email = email;
        this.memberName = memberName;
        this.categoryName = categoryName;
        this.description = description;
        this.viewCount = viewCount;
        this.createDateTime = createDateTime;
    }

    public static GetProfileResponse fromEntity(ProfileEntity entity) {
        return new GetProfileResponse(
                entity.getMemberEntity().getEmail(),
                entity.getMemberEntity().getName(),
                entity.getCategoryEntity().getCategory().getName(),
                entity.getDescription(),
                entity.getViewCount(),
                entity.getCreateDateTime());
    }
}