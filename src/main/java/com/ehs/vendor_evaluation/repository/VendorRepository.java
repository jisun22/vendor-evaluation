package com.ehs.vendor_evaluation.repository;

import com.ehs.vendor_evaluation.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    // 업체명으로 검색
    List<Vendor> findByCompanyNameContaining(String companyName);
    
    // 등급으로 검색
    List<Vendor> findByGrade(String grade);
    
    // 사업자번호로 검색
    Vendor findByBusinessNumber(String businessNumber);
}