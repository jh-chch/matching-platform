# Matching Platform

회원 조회, 조회수, 포인트 충전 (Toss PaymentKey로 결제 조회 후 회원 포인트 적립하기) 

### Docker 환경 실행

```bash
docker-compose up --build
```

- app : Spring Boot App (8081 포트)
- db : MySQL (3307 포트)
- redis : Redis (6380 포트)

<br>

## 프로젝트
### 0. 초기 데이터

애플리케이션 시작 시 자동으로 테스트 데이터가 생성됩니다.

#### 테스트 회원 데이터
| 이름 | 이메일 | 카테고리 | 설명 |
|------|--------|----------|------|
| 최정헌 | choi@test.com | IT | Java 개발자입니다. |
| 홍길동 | hong@test.com | ARCHITECTURE | 건축 설계사입니다. |
| 유관순 | yu@test.com | OFFICE | 행정 업무 경험 5년차입니다. |
| 이순신 | lee@test.com | IT | 프론트엔드 개발자입니다. |
| 고길동 | ko@test.com | ARCHITECTURE | 건축 전문가입니다. |

#### 카테고리 종류
- `IT`: IT 관련
- `ARCHITECTURE`: 건축 관련  
- `OFFICE`: 일반 사무 관련

---

### 1. 회원 프로필 API

#### 1.1 프로필 목록 조회

**GET** `/api/members/profiles`

프로필 목록을 페이지네이션과 정렬 옵션으로 조회합니다.

**Query Parameters:**
- `profileSortType`: 정렬 타입 (선택사항)
  - `NAME_ASC`: 이름 오름차순
  - `NAME_DESC`: 이름 내림차순
  - `VIEW_COUNT_ASC`: 조회수 오름차순
  - `VIEW_COUNT_DESC`: 조회수 내림차순
  - `CREATE_DATE_ASC`: 등록일 오름차순
  - `CREATE_DATE_DESC`: 등록일 내림차순 (기본값)
- `page`: 페이지 번호 (기본값: 0)
- `size`: 페이지 크기 (기본값: 20)

```bash
# 기본 조회
curl -X GET "http://localhost:8081/api/members/profiles"

# 정렬 옵션과 함께 조회
curl -X GET "http://localhost:8081/api/members/profiles?profileSortType=VIEW_COUNT_DESC&page=0&size=5"

# 페이지네이션과 정렬
curl -X GET "http://localhost:8081/api/members/profiles?page=0&size=3&sort=createDateTime,desc"
```

#### 1.2 프로필 상세 조회 (조회수 증가)

**POST** `/api/members/profile/view-count`

프로필을 조회하고 조회수를 증가시킵니다.<br>
조회수는 일정 시간 내 중복 증가 방지

```bash
curl -X POST "http://localhost:8081/api/members/profile/view-count" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ko@test.com",
    "category": "ARCHITECTURE"
  }'
```

---

### 2. 포인트 충전 API

#### 2.1 포인트 충전

**POST** `/api/point/charge`

결제 승인 확인 후후 포인트를 충전합니다.

- 포인트는 원화 1:1 비율로 충전
- Toss Payments 연동 (테스트 키 사용)
- 결제 성공 시 포인트 적립
- 중복 결제 방지 및 승인 확인 처리 포함
- 인증/인가 로직은 생략
- [Toss 결제 흐름](https://docs.tosspayments.com/guides/v2/get-started/payment-flow#요청-인증-승인)
- [Toss 결제 테스트](https://developers.tosspayments.com/sandbox)
- [Toss PaymentKey로 조회](https://docs.tosspayments.com/reference/test/v1/payments/{paymentKey}/GET)
- 사용한 결제 테스트 시크릿키: `test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm`

```bash
curl -X POST "http://localhost:8081/api/point/charge" \
  -H "Content-Type: application/json" \
  -d '{
    "pgType": "TOSS",
    "paymentKey": "5zJ4xY7m0kEnyvI8",
    "email": "ko@test.com"
  }'
```

---

### 에러 처리
에러 응답 형식:
```json
{
  "timestamp": "2024-01-15T14:30:00",
  "status": 400,
  "errorCode": "MEMBER_NOT_FOUND",
  "message": "회원을 찾을 수 없습니다.",
}
```

