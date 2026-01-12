package com.ehs.vendor_evaluation.service;

import com.ehs.vendor_evaluation.entity.Vendor;
import com.ehs.vendor_evaluation.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorService {

    private final VendorRepository vendorRepository;

    // 전체 조회
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    // 단건 조회
    public Vendor findById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("업체를 찾을 수 없습니다. ID: " + id));
    }

    // 등록
    @Transactional
    public Vendor save(Vendor vendor) {
        calculateScore(vendor);
        return vendorRepository.save(vendor);
    }

    // 수정
    @Transactional
    public Vendor update(Long id, Vendor vendorRequest) {
        Vendor vendor = findById(id);
        
        vendor.setCompanyName(vendorRequest.getCompanyName());
        vendor.setBusinessNumber(vendorRequest.getBusinessNumber());
        vendor.setRepresentative(vendorRequest.getRepresentative());
        vendor.setAddress(vendorRequest.getAddress());
        vendor.setPhone(vendorRequest.getPhone());
        vendor.setQualityScore(vendorRequest.getQualityScore());
        vendor.setDeliveryScore(vendorRequest.getDeliveryScore());
        vendor.setPriceScore(vendorRequest.getPriceScore());
        
        calculateScore(vendor);
        return vendorRepository.save(vendor);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        vendorRepository.deleteById(id);
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

    // 업체명 검색
    public List<Vendor> searchByName(String companyName) {
        return vendorRepository.findByCompanyNameContaining(companyName);
    }

    // 등급별 검색
    public List<Vendor> findByGrade(String grade) {
        return vendorRepository.findByGrade(grade);
    }
}