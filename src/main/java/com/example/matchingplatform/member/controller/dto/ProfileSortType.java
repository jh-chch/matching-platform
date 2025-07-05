package com.example.matchingplatform.member.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum ProfileSortType {
    NAME_ASC("이름 오름차순"),
    NAME_DESC("이름 내림차순"),
    VIEW_COUNT_ASC("조회수 오름차순"),
    VIEW_COUNT_DESC("조회수 내림차순"),
    CREATE_DATE_ASC("등록일 오름차순"),
    CREATE_DATE_DESC("등록일 내림차순");

    private final String description;
} 