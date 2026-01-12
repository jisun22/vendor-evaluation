package com.ehs.vendor_evaluation.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRequestDto {

    @NotBlank(message = "업체명은 필수입니다")
    @Size(max = 100, message = "업체명은 100자 이내로 입력하세요")
    private String companyName;

    @NotBlank(message = "사업자번호는 필수입니다")
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}", message = "사업자번호 형식: 000-00-00000")
    private String businessNumber;

    @Size(max = 100)
    private String representative;

    @Size(max = 200)
    private String address;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다")
    private String phone;

    @Min(value = 0, message = "점수는 0 이상이어야 합니다")
    @Max(value = 100, message = "점수는 100 이하여야 합니다")
    private Integer qualityScore;

    @Min(value = 0, message = "점수는 0 이상이어야 합니다")
    @Max(value = 100, message = "점수는 100 이하여야 합니다")
    private Integer deliveryScore;

    @Min(value = 0, message = "점수는 0 이상이어야 합니다")
    @Max(value = 100, message = "점수는 100 이하여야 합니다")
    private Integer priceScore;
}