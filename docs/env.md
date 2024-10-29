# 환경 변수 설정

애플리케이션 실행을 위해 다음의 환경 변수가 필요합니다. 

각 환경 변수는 애플리케이션의 기능에 따라 설정이 필요하니, 아래 설명을 참고하여 올바른 값을 지정해 주십시오.

## 필수 환경 변수

- 데이터베이스
- JWT 비밀키

### 데이터베이스 설정

- **`MYSQL_DB_URL`**
  - **설명**: MySQL 데이터베이스의 접속 URL입니다.
  - **예시**: `jdbc:mysql://localhost:3306/hot_deal`

- **`MYSQL_DB_PASSWORD`**
  - **설명**: MySQL 데이터베이스의 비밀번호입니다.
  - **예시**: `your_mysql_password`

### JWT 설정

- **`JWT_ACCESS_SECRET_KEY`**
  - **설명**: JWT 인증 토큰을 생성할 때 사용자 정보를 보호하기 위해 사용하는 비밀 키입니다.
  - **예시**: `your_jwt_secret_key`

## 설정 방법

환경 변수는 운영 환경에 따라 다르게 설정될 수 있습니다.

로컬 개발 시에는 `.env` 파일 또는 IDE 설정을 통해, 프로덕션 환경에서는 서버의 환경 변수로 설정하십시오.

### 예시: .env 파일 사용

`.env` 파일은 아래와 같은 위치에 위치 시키고, 파일 권한을 통해 민감 정보가 노출되지 않도록 설정해 주십시오.

```
hot-deal/
└── src/
    └── main/
        └── resources/
            └── .env     # 환경 변수 설정 파일
```

`.env` 파일 예시
```dotenv
MYSQL_DB_URL="jdbc:mysql://localhost:3306/hot_deal"
MYSQL_DB_PASSWORD="your_mysql_password"
JWT_ACCESS_SECRET_KEY="your_jwt_secret_key"
```
