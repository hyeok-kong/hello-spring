## 프로젝트 목적
공부한 스프링 프레임워크의 원리를 보다 더 깊이 이해하고, 복습하기 위한 간단한 프로젝트입니다.
### 주 목적
1. 객체 지향적인 프로젝트 설계
2. Exception Handling, Validation, AOP 등 스프링 기본 원리 및 기술
3. JPA 적용 및 쿼리 성능 확인
4. 테스트 작성 연습
5. OpenAPI Spec 적용, 문서화

## 프로젝트 스펙
- Java 17
- Springboot 3.2.5
- H2 database
- JPA

## 데이터베이스
![image](https://github.com/hyeok-kong/hello-spring/assets/70522355/044b8442-c305-4b68-bb97-f0b024fa3ec4)


- - -
## 형상 관리
### Github flow 사용
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FJebIK%2FbtsnHSEQSCt%2FswDjNqMH7DS4UdV6kDh9Y0%2Fimg.png)

기존 복잡한 로직을 갖는 Git flow 대신, 소형 프로젝트에 적합한 Github flow 방식의 branch 전략을 사용했습니다.
- - -
## 버전 관리
### OpenAPI Spec 적용
Swagger UI를 통해 API 문서를 제작, git과 함께 버전 관리를 진행합니다.
코드 변경에 따른 API 문서 변경을 추적하기에 보다 용이합니다.

[API 문서](https://github.com/hyeok-kong/hello-spring/blob/main/src/main/resources/docs/openapi_spec.yaml)

[Swagger Online Editor](https://editor.swagger.io/) 를 이용하면 UI를 통해 문서를 확인하실 수 있습니다.

- - -
## 테스트
### 단위 테스트
JUnit5 + Mockito를 통해 테스트를 진행하였습니다.

### 통합 테스트
작성 대기중입니다.
- - -

## 인증 / 권한관리
세션을 이용해 로그인을 구현하였습니다.
권한의 경우, 게시글 / 댓글의 수정/삭제 작업은 작성자만 가능하도록 구현하였습니다.

### 방법
반복되는 로그인 여부 확인, 로그인 사용자 조회 등의 로직은 어노테이션 기반으로 제작하여 가독성과 재사용성을 증가시켰습니다.

Spring의 Interceptor와 ArgumentResolver를 이용해 구현하였습니다.

## 예외 처리
ControllAdvice를 이용, 전역 예외 처리를 구현하였습니다.

기능 추가 시 각 이슈에 변경 사항 기록 예정
