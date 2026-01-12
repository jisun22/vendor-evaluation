package com.ehs.vendor_evaluation.controller;

import com.ehs.vendor_evaluation.entity.Vendor;
import com.ehs.vendor_evaluation.service.VendorService;
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
    public ResponseEntity<List<Vendor>> findAll() {
        return ResponseEntity.ok(vendorService.findAll());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.findById(id));
    }

    // 등록
    @PostMapping
    public ResponseEntity<Vendor> save(@RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.save(vendor));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> update(@PathVariable Long id, @RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.update(id, vendor));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 업체명 검색
    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> search(@RequestParam String name) {
        return ResponseEntity.ok(vendorService.searchByName(name));
    }

    // 등급별 조회
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Vendor>> findByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(vendorService.findByGrade(grade));
    }
}