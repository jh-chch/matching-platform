package com.example.matchingplatform.member.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    IT("IT"),
    ARCHITECTURE("건축"),
    OFFICE("일반 사무");

    private final String name;

    public static Category fromString(String value) {
        try {
            return Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ProfileException("존재하지 않는 카테고리입니다.");
        }
    }
}