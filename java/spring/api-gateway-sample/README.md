Smart Device Management System
==============================
API Gateway
-----------------------------

# 목차

* [요구사항](#요구사항)
* [설치방법](#설치방법)
* [환경설정](#환경설정)
* [실행환경](#실행환경)

# 요구사항

* Git(>= v2.23.0)
* JDK(>= 11)
* MariaDB(== v10.4)
* Gradle(>= v6.9.1)

# 개발환경

* __주요 라이브러리__ : Spring Boot, Gradle, lombok
* __테스트 라이브러리__ : junit

# 설치방법

이 설치 가이드는 개발환경 구축을 위함입니다.

1. 필수 요구 사항 설치
    * Git : [<u>**다운로드**</u>](https://git-scm.com/download)
    * JDK : [<u>**다운로드**</u>](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Git 저장소로부터 소스를 다운로드합니다.
3. Gradle로 관련 라이브러리를 다운로드합니다.

# 환경설정

API Gateway의 라우팅은 application.yml에 정의 되어 있습니다.  
필요한 라이브라리는 build.gradle에 정의되어 있으며 온라인으로 다운로드 가능합니다.

주요 설정 값 :

* jwt.secret : JWT 생성 시 사용된 Seed 값. IAM 모듈과 동일하게 설정해야 함.
* logging.file.name : Sl4j의 로깅 정보가 저장될 위치

적용 필터 :

* JwtAuthenticationGatewayFilter : JWT에 대한 유효성과 Role을 검증
* JwtAuthenticationGlobalFilter : JWT에 대한 유효성 검증을 위한 Global Filter, api로 시작되는 경우 jwt 유효성을 검증함
* LoggingGlobalFilter : Logging을 위한 Global Filter

# 실행환경

## Test

```shell
$ ./gradlew test
```

## Build

```shell
$ ./gradlew build
```

## 실행

```shell
$ ./gradlew bootRun
```

## 배포

build/libs에서 빌드 된 파일을 서버로 배포 후 실행

```shell
$ java -jar .\gateway-0.0.1-SNAPSHOT.jar
```