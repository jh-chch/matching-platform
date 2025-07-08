package com.example.matchingplatform.member.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewProfileRequest {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^(IT|ARCHITECTURE|OFFICE)$")
    private String category;
}
