package com.example.matchingplatform.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.matchingplatform.common.LogExecution;
import com.example.matchingplatform.payment.controller.dto.PointChargeRequest;
import com.example.matchingplatform.payment.controller.dto.PointChargeResponse;
import com.example.matchingplatform.payment.service.PointChargeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointApiController {

    private final PointChargeService pointChargeService;

    @PostMapping("/charge")
    @LogExecution("포인트 충전")
    public ResponseEntity<PointChargeResponse> charge(@RequestBody PointChargeRequest request) {
        var charge = pointChargeService.charge(request);
        return ResponseEntity.ok(charge);
    }
}
