# ===== 1단계: 빌드 스테이지 =====
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Gradle Wrapper 및 설정 파일 먼저 복사 (의존성 캐싱 최적화)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
# build.gradle.kts, settings.gradle.kts를 쓰는 경우 위 두 줄을 아래로 교체
# COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew

# 의존성만 먼저 받아서 레이어 캐싱 (소스 변경 시 재다운로드 방지)
RUN ./gradlew dependencies --no-daemon || return 0

# 전체 소스 복사 후 빌드
COPY src src

RUN ./gradlew bootJar --no-daemon -x test

# ===== 2단계: 실행 스테이지 =====
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# 보안을 위해 non-root 사용자 생성
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

# 빌드 스테이지에서 생성된 jar만 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
