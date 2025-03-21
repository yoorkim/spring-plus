# [SPRING PLUS]

## 📖 과제 소개
### Lv1. 코드 개선
    - @Transactional의 이해
    - JWT의 이해
    - JPA의 이해
    - Controller Test의 이해
    - AOP의 이해
### Lv2. JPA 심화
    - JPA Cascade
    - N+1 문제
    - QueryDSL
    - Spring Security
      - stateless JWT 방식의 Spring Security
### Lv3. 성능 최적화
    - QueryDSL을 활용한 쿼리 최적화
    - Transaction 심화
      - @Transactional(propagation = Propagation.REQUIRES_NEW) 
    - AWS 활용
      - EC2
      - RDS
      - S3
    - 대용량 데이터 처리
      - 100만 건의 데이터 생성
      - 조회 속도 개선

---
## ⚙️ AWS 활용
### 1️⃣ EC2
- #### Elastic IP를 설정한 인스턴스 생성
<img width="100%" alt="인스턴스1" src="https://github.com/user-attachments/assets/79d544d2-18df-493c-b78f-944b93685f61" />

- #### 보안 그룹 규칙 추가
    - 포트 범위 : 22, 8080, 443, 80

<img width="100%" alt="인스턴스2" src="https://github.com/user-attachments/assets/cfba3d53-a433-45cc-9c02-dc72aaf8e421" />

- #### health check API 접속
<img width="100%" alt="health" src="https://github.com/user-attachments/assets/44bf459e-a496-4685-b16f-7dcc804e3379" />

<br>

### 2️⃣ RDS
- #### RDS에 db 생성
<img width="100%" alt="2  RDS_정보" src="https://github.com/user-attachments/assets/d573bbeb-5399-43b1-b0eb-4ae225025572" />

- #### 회원가입 테스트
<img width="100%" alt="2  RDS_test" src="https://github.com/user-attachments/assets/ad76d4c3-ba6b-47f5-994d-7d1408c42be2" />

- #### db 연결 확인
<img width="100%" alt="2  RDS_db" src="https://github.com/user-attachments/assets/88df7c27-6e82-4ed1-ae09-07b811d8149a" />

<br>
<br>

### 3️⃣ S3

- #### S3 버킷 생성 후 프로필 이미지 업로드
<img width="100%" alt="3  upload   get" src="https://github.com/user-attachments/assets/fafc8520-9dc6-4bb9-8a28-dd7091a7b3de" />

- #### 버킷에 업로드된 이미지 확인
<img width="100%" alt="3  bucket2" src="https://github.com/user-attachments/assets/61c559b1-ca22-4ec6-9547-5b43552dd797" />

---
## 💡 대용량 데이터 처리
- #### 유저 데이터 100만 건 생성
<img width="100%" alt="user_data" src="https://github.com/user-attachments/assets/8c76574b-bf65-4a30-a686-934c185f1d66" />

- #### nickname 컬럼에 INDEX 적용
    - `CREATE INDEX idx_nickname ON users(nickname);`

- #### INDEX 적용 전/후 속도 측정
    - 100만번째 유저 조회 속도 9배 빨라짐

<img width="100%" alt="13" src="https://github.com/user-attachments/assets/ee1b33a6-ce50-4a2f-8890-bd8abe1f5bd5" />