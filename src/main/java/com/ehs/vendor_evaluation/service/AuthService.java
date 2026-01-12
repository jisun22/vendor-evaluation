package com.ehs.vendor_evaluation.service;

import com.ehs.vendor_evaluation.dto.AuthRequestDto;
import com.ehs.vendor_evaluation.dto.AuthResponseDto;
import com.ehs.vendor_evaluation.entity.User;
import com.ehs.vendor_evaluation.repository.UserRepository;
import com.ehs.vendor_evaluation.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public AuthResponseDto register(AuthRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다: " + request.getUsername());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName() != null ? request.getName() : request.getUsername())
                .role("USER")
                .build();

        User saved = userRepository.save(user);

        String token = jwtTokenProvider.createToken(saved.getUsername(), saved.getRole());

        return AuthResponseDto.builder()
                .token(token)
                .username(saved.getUsername())
                .name(saved.getName())
                .role(saved.getRole())
                .build();
    }

    // 로그인
    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());

        return AuthResponseDto.builder()
                .token(token)
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}