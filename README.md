# 🏰 Crimson Citadel (크림슨 시타델) 

    Spring Boot 기반의 텍스트 RPG 백엔드 과제 프로젝트입니다. 
    Spring의 핵심 기능(IoC/DI, 계층형 아키텍처)을 학습하기 위해 제작되었습니다.

## 기술 스택

언어 및 프레임워크: Java 21, Spring Boot

데이터 관리: Spring Data JPA / MySQL

빌드 도구: Gradle

## 주요 기능

생성 : 플레이어 생성

게임 : 카드를 사용하여 전투. 진행상황을 저장 및 갱신하며 진행

이름 변경 : 캐릭터의 이름 변경

삭제 : 게임과 관련된 모든 정보 삭제

예외 처리 및 검증: 잘못된 요청에 대한 유효성 검사

## 시작 가이드

### 1. 저장소 복제

Bash

    git clone https://github.com/kimdandy/game-spring-basic-assignment_Crimson-Citadel.gitcd game-spring-basic-assignment_Crimson-Citadel

### 2. 빌드 및 실행

Bash

    ./gradlew bootRun

### 3. API 테스트 또는 콘솔 실행

로컬 서버 포트: http://localhost:8080


## 디렉터리 구조

    src/main/java/
    └── com/gamebasic/
        ├── common
        ├── game                # 게임 관련
            ├── entity/          # 도메인 객체 모델 
            ├── controller/      # 웹 요청 처리 및 라우팅
            ├── service/         # 핵심 비즈니스 및 로직
            ├── repository/      # 데이터베이스 연동 계층
            └── dto/             # 데이터 컨테이너
        ├── runcard             # 카드 관련     
            ├── entity/          
            ├── repository/      
            └── dto/             
        └── GameBasicApplication
