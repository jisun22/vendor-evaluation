package com.ehs.vendor_evaluation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    @NotBlank(message = "아이디를 입력하세요")
    @Size(min = 4, max = 50, message = "아이디는 4~50자 사이로 입력하세요")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 4, message = "비밀번호는 4자 이상 입력하세요")
    private String password;

    private String name;  // 회원가입 시에만 사용
}