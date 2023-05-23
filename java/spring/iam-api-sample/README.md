IAM API Service Sample
==============================

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
* __인증 라이브러리__ : Spring boot security, JWT
* __문서화 라이브러리__ : openapi3, restdocs, swagger-ui
* __테스트 라이브러리__ : junit, rest-assured
* __템플릿 엔진__ : thymeleaf

# 설치방법

이 설치 가이드는 개발환경 구축을 위함입니다.

1. 필수 요구 사항 설치
    * Git : [<u>**다운로드**</u>](https://git-scm.com/download)
    * JDK : [<u>**다운로드**</u>](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Git 저장소로부터 소스를 다운로드합니다.
3. Gradle로 관련 라이브러리를 다운로드합니다.
4. MariaDB에 데이터베이스를 생성합니다.

# 환경설정

서버의 기본설정은 JavaConfig에 정의 되어 있으며, 외부 환경 설정 요인은 application-{local/test/product}.yml에 정의 되어있습니다.  
필요한 라이브라리는 build.gradle에 정의되어 있으며 온라인으로 다운로드 가능합니다.

주요 설정 값 :

* spring.profiles.active : 적용할 Profiles 설정
* spring.jpa.hibernate.ddl-auto : hibernate 초기화 여부 설정
* spring.sql.init.mode : /resources/sql 하위에 있는 sql 파일 실행 여부 설정
* spring.datasource : DB datasource 정보 설정

# 실행환경

## Test

Test를 위한 Token은 test/resources/token.yml에 정의 되어 있음.

```shell
$ ./gradlew test
```

## Build

```shell
$ ./gradlew build
```

## 실행

```shell
$ ./gradlew bootRun -Dspring.profiles.active=local
```

## 배포

build/libs에서 빌드 된 파일을 서버로 배포 후 실행

```shell
$ java -jar .\build\libs\iam-api-sample-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```