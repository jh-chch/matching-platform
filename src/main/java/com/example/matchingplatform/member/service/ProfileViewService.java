package com.example.matchingplatform.member.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.matchingplatform.member.repository.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileViewService {

    private final StringRedisTemplate redisTemplate;
    private static final long VIEW_BLOCK_SECONDS = 60;

    /**
     * 프로필 조회 시 중복 조회를 방지하고, 일정 시간 내에
     * 동일 사용자가 동일 프로필을 여러 번 조회해도 조회수가
     * 중복 증가하지 않도록 처리
     * 
     * @param profileEntity 조회 프로필 엔티티 객체
     */
    public void handleView(ProfileEntity profileEntity, String viewerIp) {
        Long profileId = profileEntity.getId();

        if (markViewed(viewerIp, profileId)) {
            profileEntity.increaseViewCount();
        }
    }

    public boolean markViewed(String viewerIp, Long profileId) {
        String key = generateKey(viewerIp, profileId);
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "1", Duration.ofSeconds(VIEW_BLOCK_SECONDS));
        return Boolean.TRUE.equals(success);
    }

    private String generateKey(String viewerIp, Long profileId) {
        return "viewerIp:" + viewerIp + ":" + profileId;
    }
}
