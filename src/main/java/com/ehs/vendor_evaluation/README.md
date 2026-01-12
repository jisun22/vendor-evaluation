# 협력업체 평가 시스템 (Vendor Evaluation System)

EHS(환경안전) 도메인 기반 협력업체 평가 관리 REST API

## 🛠 기술 스택

- **Backend:** Java 21, Spring Boot 4.0
- **Database:** MySQL 9.2
- **ORM:** Spring Data JPA, Hibernate
- **Build:** Maven

## 📋 주요 기능

- 협력업체 CRUD (등록/조회/수정/삭제)
- 평가 점수 입력 및 자동 계산
- 등급 자동 산정 (우수/보통/개선필요)
- 업체명 검색, 등급별 조회

## 📊 평가 기준

| 항목 | 가중치 |
|------|--------|
| 품질 점수 | 40% |
| 납기 점수 | 30% |
| 가격 점수 | 30% |

| 등급 | 기준 |
|------|------|
| 우수 | 80점 이상 |
| 보통 | 60~79점 |
| 개선필요 | 60점 미만 |

## 🚀 API 엔드포인트

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | /api/vendors | 전체 조회 |
| GET | /api/vendors/{id} | 단건 조회 |
| POST | /api/vendors | 등록 |
| PUT | /api/vendors/{id} | 수정 |
| DELETE | /api/vendors/{id} | 삭제 |
| GET | /api/vendors/search?name= | 업체명 검색 |
| GET | /api/vendors/grade/{grade} | 등급별 조회 |

## 💻 실행 방법
```bash
# 1. MySQL 데이터베이스 생성
CREATE DATABASE vendor_evaluation;

# 2. application.properties 설정 (DB 정보 수정)

# 3. 실행
./mvnw spring-boot:run
```

## 📝 요청/응답 예시

### 등록 요청
```json
POST /api/vendors
{
  "companyName": "삼성전자",
  "businessNumber": "123-45-67890",
  "representative": "홍길동",
  "address": "서울시 강남구",
  "phone": "02-1234-5678",
  "qualityScore": 85,
  "deliveryScore": 90,
  "priceScore": 80
}
```

### 응답
```json
{
  "id": 1,
  "companyName": "삼성전자",
  "businessNumber": "123-45-67890",
  "representative": "홍길동",
  "address": "서울시 강남구",
  "phone": "02-1234-5678",
  "qualityScore": 85,
  "deliveryScore": 90,
  "priceScore": 80,
  "totalScore": 85.0,
  "grade": "우수",
  "createdAt": "2026-01-12T11:31:09",
  "updatedAt": "2026-01-12T11:31:09"
}
```