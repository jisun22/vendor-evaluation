package com.ehs.vendor_evaluation.dto;

import com.ehs.vendor_evaluation.entity.Vendor;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponseDto {

    private Long id;
    private String companyName;
    private String businessNumber;
    private String representative;
    private String address;
    private String phone;
    private Integer qualityScore;
    private Integer deliveryScore;
    private Integer priceScore;
    private Double totalScore;
    private String grade;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> DTO 변환
    public static VendorResponseDto from(Vendor vendor) {
        return VendorResponseDto.builder()
                .id(vendor.getId())
                .companyName(vendor.getCompanyName())
                .businessNumber(vendor.getBusinessNumber())
                .representative(vendor.getRepresentative())
                .address(vendor.getAddress())
                .phone(vendor.getPhone())
                .qualityScore(vendor.getQualityScore())
                .deliveryScore(vendor.getDeliveryScore())
                .priceScore(vendor.getPriceScore())
                .totalScore(vendor.getTotalScore())
                .grade(vendor.getGrade())
                .createdAt(vendor.getCreatedAt())
                .updatedAt(vendor.getUpdatedAt())
                .build();
    }
}