package com.example.matchingplatform.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.matchingplatform.member.controller.dto.GetProfileResponse;
import com.example.matchingplatform.member.controller.dto.ProfileSortType;
import com.example.matchingplatform.member.controller.dto.ViewProfileRequest;
import com.example.matchingplatform.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/profiles")
    public ResponseEntity<Page<GetProfileResponse>> getProfileList(
            ProfileSortType profileSortType,
            Pageable pageable) {
        var profileList = memberService.getProfileList(profileSortType, pageable);
        return ResponseEntity.ok(profileList);
    }

    /**
     * 프로필 상세 조회 + 조회수 증가 처리 -> 두 동작을 하나의 요청으로 처리함
     * 단순 조회가 아닌 조회수 증가 상태 변경 포함
     */
    @PostMapping("/profile/view-count")
    public ResponseEntity<GetProfileResponse> viewCount(@RequestBody ViewProfileRequest request) {
        GetProfileResponse response = memberService.viewCount(request);
        return ResponseEntity.ok(response);
    }
}
