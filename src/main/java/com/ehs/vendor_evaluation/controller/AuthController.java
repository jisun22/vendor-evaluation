package com.ehs.vendor_evaluation.controller;

import com.ehs.vendor_evaluation.dto.ApiResponse;
import com.ehs.vendor_evaluation.dto.AuthRequestDto;
import com.ehs.vendor_evaluation.dto.AuthResponseDto;
import com.ehs.vendor_evaluation.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDto>> register(
            @Valid @RequestBody AuthRequestDto request) {
        AuthResponseDto response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공", response));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @Valid @RequestBody AuthRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", response));
    }
}