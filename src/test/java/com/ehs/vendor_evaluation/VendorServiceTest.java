package com.ehs.vendor_evaluation;

import com.ehs.vendor_evaluation.dto.VendorRequestDto;
import com.ehs.vendor_evaluation.dto.VendorResponseDto;
import com.ehs.vendor_evaluation.repository.VendorRepository;
import com.ehs.vendor_evaluation.service.VendorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VendorServiceTest {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorRepository vendorRepository;

    private VendorRequestDto createTestVendor() {
        return VendorRequestDto.builder()
                .companyName("테스트기업")
                .businessNumber("123-45-67890")
                .representative("홍길동")
                .address("서울시 강남구")
                .phone("02-1234-5678")
                .qualityScore(85)
                .deliveryScore(90)
                .priceScore(80)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("협력업체 등록 테스트")
    void saveTest() {
        // given
        VendorRequestDto request = createTestVendor();

        // when
        VendorResponseDto response = vendorService.save(request);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getCompanyName()).isEqualTo("테스트기업");
        assertThat(response.getTotalScore()).isEqualTo(85.0);
        assertThat(response.getGrade()).isEqualTo("우수");
    }

    @Test
    @Order(2)
    @DisplayName("점수 계산 검증 - 우수 등급")
    void calculateScoreExcellentTest() {
        // given
        VendorRequestDto request = VendorRequestDto.builder()
                .companyName("우수기업")
                .businessNumber("111-11-11111")
                .qualityScore(90)
                .deliveryScore(85)
                .priceScore(80)
                .build();

        // when
        VendorResponseDto response = vendorService.save(request);

        // then
        // (90 * 0.4) + (85 * 0.3) + (80 * 0.3) = 36 + 25.5 + 24 = 85.5
        assertThat(response.getTotalScore()).isEqualTo(85.5);
        assertThat(response.getGrade()).isEqualTo("우수");
    }

    @Test
    @Order(3)
    @DisplayName("점수 계산 검증 - 보통 등급")
    void calculateScoreNormalTest() {
        // given
        VendorRequestDto request = VendorRequestDto.builder()
                .companyName("보통기업")
                .businessNumber("222-22-22222")
                .qualityScore(70)
                .deliveryScore(65)
                .priceScore(60)
                .build();

        // when
        VendorResponseDto response = vendorService.save(request);

        // then
        // (70 * 0.4) + (65 * 0.3) + (60 * 0.3) = 28 + 19.5 + 18 = 65.5
        assertThat(response.getTotalScore()).isEqualTo(65.5);
        assertThat(response.getGrade()).isEqualTo("보통");
    }

    @Test
    @Order(4)
    @DisplayName("점수 계산 검증 - 개선필요 등급")
    void calculateScorePoorTest() {
        // given
        VendorRequestDto request = VendorRequestDto.builder()
                .companyName("개선기업")
                .businessNumber("333-33-33333")
                .qualityScore(50)
                .deliveryScore(50)
                .priceScore(50)
                .build();

        // when
        VendorResponseDto response = vendorService.save(request);

        // then
        assertThat(response.getTotalScore()).isEqualTo(50.0);
        assertThat(response.getGrade()).isEqualTo("개선필요");
    }

    @Test
    @Order(5)
    @DisplayName("협력업체 수정 테스트")
    void updateTest() {
        // given
        VendorRequestDto request = createTestVendor();
        VendorResponseDto saved = vendorService.save(request);

        VendorRequestDto updateRequest = VendorRequestDto.builder()
                .companyName("수정된기업")
                .businessNumber("123-45-67890")
                .qualityScore(95)
                .deliveryScore(95)
                .priceScore(95)
                .build();

        // when
        VendorResponseDto updated = vendorService.update(saved.getId(), updateRequest);

        // then
        assertThat(updated.getCompanyName()).isEqualTo("수정된기업");
        assertThat(updated.getTotalScore()).isEqualTo(95.0);
    }

    @Test
    @Order(6)
    @DisplayName("존재하지 않는 업체 조회 시 예외 발생")
    void findByIdNotFoundTest() {
        // when & then
        assertThatThrownBy(() -> vendorService.findById(9999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("업체를 찾을 수 없습니다");
    }
}