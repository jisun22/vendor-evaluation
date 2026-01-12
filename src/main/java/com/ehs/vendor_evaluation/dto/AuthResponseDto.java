package com.ehs.vendor_evaluation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    private String token;
    private String username;
    private String name;
    private String role;
}