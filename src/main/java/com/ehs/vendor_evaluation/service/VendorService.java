package com.ehs.vendor_evaluation.service;

import com.ehs.vendor_evaluation.dto.VendorRequestDto;
import com.ehs.vendor_evaluation.dto.VendorResponseDto;
import com.ehs.vendor_evaluation.entity.Vendor;
import com.ehs.vendor_evaluation.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorService {

    private final VendorRepository vendorRepository;

    // 전체 조회
    public List<VendorResponseDto> findAll() {
        return vendorRepository.findAll().stream()
                .map(VendorResponseDto::from)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public VendorResponseDto findById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. ID: " + id));
        return VendorResponseDto.from(vendor);
    }

    // 등록
    @Transactional
    public VendorResponseDto save(VendorRequestDto requestDto) {
        Vendor vendor = toEntity(requestDto);
        calculateScore(vendor);
        Vendor saved = vendorRepository.save(vendor);
        return VendorResponseDto.from(saved);
    }

    // 수정
    @Transactional
    public VendorResponseDto update(Long id, VendorRequestDto requestDto) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. ID: " + id));

        vendor.setCompanyName(requestDto.getCompanyName());
        vendor.setBusinessNumber(requestDto.getBusinessNumber());
        vendor.setRepresentative(requestDto.getRepresentative());
        vendor.setAddress(requestDto.getAddress());
        vendor.setPhone(requestDto.getPhone());
        vendor.setQualityScore(requestDto.getQualityScore());
        vendor.setDeliveryScore(requestDto.getDeliveryScore());
        vendor.setPriceScore(requestDto.getPriceScore());

        calculateScore(vendor);
        Vendor updated = vendorRepository.save(vendor);
        return VendorResponseDto.from(updated);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new IllegalArgumentException("업체를 찾을 수 없습니다. ID: " + id);
        }
        vendorRepository.deleteById(id);
    }

    // 업체명 검색
    public List<VendorResponseDto> searchByName(String companyName) {
        return vendorRepository.findByCompanyNameContaining(companyName).stream()
                .map(VendorResponseDto::from)
                .collect(Collectors.toList());
    }

    // 등급별 검색
    public List<VendorResponseDto> findByGrade(String grade) {
        return vendorRepository.findByGrade(grade).stream()
                .map(VendorResponseDto::from)
                .collect(Collectors.toList());
    }

    // DTO -> Entity 변환
    private Vendor toEntity(VendorRequestDto dto) {
        return Vendor.builder()
                .companyName(dto.getCompanyName())
                .businessNumber(dto.getBusinessNumber())
                .representative(dto.getRepresentative())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .qualityScore(dto.getQualityScore())
                .deliveryScore(dto.getDeliveryScore())
                .priceScore(dto.getPriceScore())
                .build();
    }

    // 점수 계산 및 등급 산정
    private void calculateScore(Vendor vendor) {
        if (vendor.getQualityScore() != null &&
            vendor.getDeliveryScore() != null &&
            vendor.getPriceScore() != null) {

            double total = (vendor.getQualityScore() * 0.4) +
                          (vendor.getDeliveryScore() * 0.3) +
                          (vendor.getPriceScore() * 0.3);

            vendor.setTotalScore(Math.round(total * 10) / 10.0);

            if (total >= 80) {
                vendor.setGrade("우수");
            } else if (total >= 60) {
                vendor.setGrade("보통");
            } else {
                vendor.setGrade("개선필요");
            }
        }
    }
}