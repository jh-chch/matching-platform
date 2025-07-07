package com.example.matchingplatform.member.service;

import static com.example.matchingplatform.member.service.MemberServiceErrorCode.PROFILE_NOT_FOUND;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.matchingplatform.member.controller.dto.GetProfileResponse;
import com.example.matchingplatform.member.controller.dto.ProfileSortType;
import com.example.matchingplatform.member.controller.dto.ViewProfileRequest;
import com.example.matchingplatform.member.repository.ProfileJpaRepository;
import com.example.matchingplatform.member.repository.ProfileQueryRepository;
import com.example.matchingplatform.member.repository.entity.Category;
import com.example.matchingplatform.member.repository.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ProfileJpaRepository profileJpaRepository;
    private final ProfileQueryRepository profileQueryRepository;
    private final ProfileViewService profileViewService;

    public Page<GetProfileResponse> getProfileList(ProfileSortType profileSortType, Pageable pageable) {
        return profileQueryRepository.findAllProfiles(profileSortType, pageable);
    }

    @Transactional
    public GetProfileResponse viewCount(ViewProfileRequest request, String viewerIp) {
        // 회원 프로필 조회
        ProfileEntity profileEntity = profileJpaRepository
                .findByEmailAndCategory(request.getEmail(), Category.fromString(request.getCategory()))
                .orElseThrow(() -> new MemberServiceException(PROFILE_NOT_FOUND));

        // 조회수 증가 처리
        profileViewService.handleView(profileEntity, viewerIp);

        return GetProfileResponse.fromEntity(profileEntity);
    }
}
