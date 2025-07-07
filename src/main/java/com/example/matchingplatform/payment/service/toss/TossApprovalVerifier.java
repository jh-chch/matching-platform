package com.example.matchingplatform.payment.service.toss;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.matchingplatform.payment.service.PaymentApprovalResult;
import com.example.matchingplatform.payment.service.PaymentApprovalVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component("TOSS")
@RequiredArgsConstructor
public class TossApprovalVerifier implements PaymentApprovalVerifier {

    private final ObjectMapper objectMapper;

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    private static final String BASE_URL = "https://api.tosspayments.com/v1";

    private final WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();

    @Override
    public PaymentApprovalResult verifyApproved(String paymentKey) {
        String encodedKey = Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        try {
            var response = webClient.get()
                    .uri("/payments/{paymentKey}", paymentKey)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedKey)
                    .retrieve()
                    .bodyToMono(TossApprovalVerifierResponse.class)
                    .block();

            if (!"DONE".equals(response.getStatus())) {
                return PaymentApprovalResult.failure("결제 승인 상태가 아님");
            }

            return PaymentApprovalResult.success(response.getTotalAmount(), response.getApprovedAt());

        } catch (WebClientResponseException e) {
            try {
                String errorMessage = objectMapper.readTree(e.getResponseBodyAsString())
                        .path("message").asText("알 수 없는 오류");
                return PaymentApprovalResult.failure("Toss 결제 조회 실패: " + errorMessage);
            } catch (JsonProcessingException jsonEx) {
                return PaymentApprovalResult.failure("Toss 결제 조회 실패: 응답 파싱 오류 (" + e.getStatusCode() + ")");
            }

        } catch (Exception e) {
            return PaymentApprovalResult.failure("Toss 결제 조회 실패: 시스템 오류 (" + e.getMessage() + ")");
        }
    }

}
