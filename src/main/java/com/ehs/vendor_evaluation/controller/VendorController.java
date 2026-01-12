package com.ehs.vendor_evaluation.controller;

import com.ehs.vendor_evaluation.dto.ApiResponse;
import com.ehs.vendor_evaluation.dto.VendorRequestDto;
import com.ehs.vendor_evaluation.dto.VendorResponseDto;
import com.ehs.vendor_evaluation.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<VendorResponseDto>>> findAll() {
        List<VendorResponseDto> vendors = vendorService.findAll();
        return ResponseEntity.ok(ApiResponse.success("협력업체 목록 조회 성공", vendors));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponseDto>> findById(@PathVariable Long id) {
        VendorResponseDto vendor = vendorService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("협력업체 조회 성공", vendor));
    }

    // 등록
    @PostMapping
    public ResponseEntity<ApiResponse<VendorResponseDto>> save(@Valid @RequestBody VendorRequestDto requestDto) {
        VendorResponseDto saved = vendorService.save(requestDto);
        return ResponseEntity.ok(ApiResponse.success("협력업체 등록 성공", saved));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody VendorRequestDto requestDto) {
        VendorResponseDto updated = vendorService.update(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("협력업체 수정 성공", updated));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("협력업체 삭제 성공", null));
    }

    // 업체명 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<VendorResponseDto>>> search(@RequestParam String name) {
        List<VendorResponseDto> vendors = vendorService.searchByName(name);
        return ResponseEntity.ok(ApiResponse.success("검색 성공", vendors));
    }

    // 등급별 조회
    @GetMapping("/grade/{grade}")
    public ResponseEntity<ApiResponse<List<VendorResponseDto>>> findByGrade(@PathVariable String grade) {
        List<VendorResponseDto> vendors = vendorService.findByGrade(grade);
        return ResponseEntity.ok(ApiResponse.success("등급별 조회 성공", vendors));
    }
}