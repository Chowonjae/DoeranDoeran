# 도란도란

## ![도란도란](./DOCS/도란도란.png)

## 도란도란 바로가기 : [도란도란](https://play.google.com/store/apps/details?id=com.purple.hello&hl=ko&pli=1)

<!-- ## UCC 보러가기 : [UCC!!](https://www.youtube.com/) -->

## 프로젝트 진행 기간

2023.04.10(월) ~ 2023.05.26(금)

SSAFY 8기 2학기 자율프로젝트

## 기획 의도 및 배경

여러분들은 가족과 또는 지인들과 얼마나 많이, 얼마나 자주 안부를 주고 받나요?<br/>
친하다는 이유로, 가장 가까운 사람이라는 이유로 안부를 물어보는데 소홀하지 않았나요?<br/>
지금부터 **도란도란**을 사용하여 가족과 지인들에게 안부를 전해보세요!<br/>
매일매일 찾아오는 간단한 질문에 사진과 글로 간편하게 피드에 남겨보세요. 평소에 안부 전하기 부담스러웠다면 **도란도란**으로 안부를 전하기 충분할 것입니다!!

## 주요 기능

- Use Application
  - 매일매일 새로운 질문이 갱신된다.
  - 구성원들이 새로운 질문에 답을 하면서 서로의 안부를 확인한다.
- Alarm
  - 사용자가 24시간 동안 움직임이 없을 경우 가족에게 안전 알림 전송
  - 사용자가 위험에 노출되었을 경우 가족에게 안전 알림을 전송
  - 피드 입력 마감까지 글을 올리지 않았을 경우 글을 올려달라는 알림을 전송

## 💡주목 포인트

- 질문의 다양성을 위한 베이지안 알고리즘 적용
- FCM을 통한 알림 서버 구축
- AWS S3를 통해서 사용자들이 올리는 피드 사진들을 저장할 수 있는 서버 구축
- 다이렉트 링크를 통한 사용자 초대

## 주요 기술

**Backend - Spring, Flask**

- IntelliJ Ultimate 2022.3.3
- Pycharm Community Edition 2022.3.1
- Spring Boot Gradle 2.7.9
- Spring Data JPA
- QueryDSL 1.0.10
- Junit 5
- SonarQube
- Flask
- JWT
- OAuth
- MySQL 8.0.31

**Frontend - Android**

- Android Studio 2022.2.1
- Kotlin
- Jetpack
  - composeUI
  - navigation
  - mvvm
- Room
- Retrofit
- Hilt
- FCM(Firebase)
- Workmanager
- Broadcastrecieve
- Protodatastore
- Kotlinx-serialization Json
- Coil

**CI/CD**

- AWS EC2
- AWS S3
- NGINX
- Docker
- SSL (certbot)

## 프로젝트 파일 구조

```
S08P31A408
├── backend
│   ├── hello
│   │   ├── build
│   │   │   ├── classes
│   │   │   │   └── java
│   │   │   │       └── main
│   │   │   │           └── com
│   │   │   │               └── purple
│   │   │   │                   └── hello
│   │   │   │                       ├── config
│   │   │   │                       ├── controller
│   │   │   │                       ├── dao
│   │   │   │                       │   └── impl
│   │   │   │                       ├── dto
│   │   │   │                       │   ├── in
│   │   │   │                       │   ├── out
│   │   │   │                       │   └── tool
│   │   │   │                       ├── encoder
│   │   │   │                       ├── entity
│   │   │   │                       ├── enu
│   │   │   │                       ├── exception
│   │   │   │                       ├── filters
│   │   │   │                       ├── generator
│   │   │   │                       ├── jwt
│   │   │   │                       ├── repo
│   │   │   │                       ├── scheduler
│   │   │   │                       ├── service
│   │   │   │                       │   └── impl
│   │   │   │                       └── util
│   │   │   ├── generated
│   │   │   │   ├── querydsl
│   │   │   │   │   └── com
│   │   │   │   │       └── purple
│   │   │   │   │           └── hello
│   │   │   │   │               └── entity
│   │   │   │   └── sources
│   │   │   │       ├── annotationProcessor
│   │   │   │       │   └── java
│   │   │   │       │       └── main
│   │   │   │       └── headers
│   │   │   │           └── java
│   │   │   │               └── main
│   │   │   ├── libs
│   │   │   ├── resources
│   │   │   │   └── main
│   │   │   │       └── properties
│   │   │   └── tmp
│   │   │       ├── bootJar
│   │   │       ├── compileJava
│   │   │       │   └── compileTransaction
│   │   │       │       ├── annotation-output
│   │   │       │       ├── compile-output
│   │   │       │       │   └── com
│   │   │       │       │       └── purple
│   │   │       │       │           └── hello
│   │   │       │       │               ├── config
│   │   │       │       │               ├── controller
│   │   │       │       │               ├── dao
│   │   │       │       │               │   └── impl
│   │   │       │       │               ├── dto
│   │   │       │       │               │   ├── in
│   │   │       │       │               │   ├── out
│   │   │       │       │               │   └── tool
│   │   │       │       │               ├── entity
│   │   │       │       │               ├── exception
│   │   │       │       │               ├── filters
│   │   │       │       │               ├── generator
│   │   │       │       │               ├── jwt
│   │   │       │       │               ├── repo
│   │   │       │       │               ├── scheduler
│   │   │       │       │               ├── service
│   │   │       │       │               │   └── impl
│   │   │       │       │               └── util
│   │   │       │       ├── header-output
│   │   │       │       └── stash-dir
│   │   │       └── compileQuerydsl
│   │   ├── gradle
│   │   │   └── wrapper
│   │   └── src
│   │       ├── main
│   │       │   ├── java
│   │       │   │   └── com
│   │       │   │       └── purple
│   │       │   │           └── hello
│   │       │   │               ├── config
│   │       │   │               ├── controller
│   │       │   │               ├── dao
│   │       │   │               │   └── impl
│   │       │   │               ├── dto
│   │       │   │               │   ├── in
│   │       │   │               │   ├── out
│   │       │   │               │   └── tool
│   │       │   │               ├── encoder
│   │       │   │               ├── entity
│   │       │   │               ├── enu
│   │       │   │               ├── exception
│   │       │   │               ├── filters
│   │       │   │               ├── generator
│   │       │   │               ├── jwt
│   │       │   │               ├── python
│   │       │   │               ├── repo
│   │       │   │               ├── scheduler
│   │       │   │               ├── service
│   │       │   │               │   └── impl
│   │       │   │               └── util
│   │       │   └── resources
│   │       │       └── properties
│   │       └── test
│   │           └── java
│   │               └── com
│   │                   └── purple
│   │                       └── hello
│   │                           ├── controller
│   │                           ├── dao
│   │                           ├── mock
│   │                           ├── service
│   │                           └── test
│   └── hello@tmp
├── android
│   └── app
│       ├── feature
│       │   ├── model
│       │   │   └── domain
│       │   └── designsystem
│       ├── notification
│       │   └── notification
│       │       └── notification
│       ├── room
│       │   ├── account
│       │   │   └── account
│       │   └── rooms
│       │       └── rooms
│       ├── setting:app
│       │   └── account
│       │       └── account
│       ├── setting:profile
│       │   └── user
│       │       └── user
│       ├── setting:room
│       │   └── rooms
│       │       └── rooms
│       └── sync: work
│           ├── database
│           ├── network
│           └── datastore
├── data
│   ├── certbot
│   │   └── www
│   └── nginx
├── database
│   ├── env
│   └── mysql
│       └── res
│           └── data
└── python
    └── flask
        └── Logic

```

## 협업 툴

- GitLab
- Notion
- JIRA
- Slack
- MatterMost

## 협업 환경

- Gitlab
  - 코드의 버전을 관리
  - 기능 완료 후 병합 과정에서 코드리뷰 진행
- JIRA
  - 매주 Sprint 진행
  - 업무마다 Story Point를 부여하고 주당 40point씩 수행
- 회의
  - 아침마다 스크럼 회의 진행
  - 전날 한 일과 당일 할 일 브리핑
  - 서로 담당 업무와 진행 상황을 알아 문제 발생 시 빠르게 대처
- Notion
  - 회의록을 기록하여 보관
  - 아이디어, 와이어프레임, ERD, API 명세서 등 모두가 공유해야 하는 문서 관리
  - 컨벤션 정리

## 컨벤션

## **Git**

### 커밋 분류 규칙

- **Init** - 프로젝트 시작
- **Feat** - 새로운 기능 추가
- **Fix** - 버그 수정
- **Res** - res 패키지 수정
- **Layout** - 레이아웃 파일 추가 및 변경
- **Refactor** - 프로덕션 코드 리팩토링
- **Comment** - 필요한 주석 추가 및 변경
- **Build** - 빌드 관련 파일 수정
- **Ci** - CI관련 설정 수정
- **Docs** - 문서 (문서 추가, 수정, 삭제)
- **Test** - 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없는 경우)
- **Chore** - 기타 변경사항 (빌드 스크립트 수정 등)

### 커밋 규칙

1. 메시지 규칙
   1. 태그이름: 행위 - 상세 설명
   2. ex) Feat: 추가 - 로그인, User 정보를 서버에서 받는 Rest API 추가
   3. 무조건 명사형으로 마무리 ex) 작성, 추가 등
   4. 마지막에 특수문자는 삽입하지 않기
2. 커밋 시기
   1. 개인이 알아서
   2. 1일 1커밋 권장
   3. 커밋 시 모두에게 언급하기

### 머지 규칙

1. 메시지 규칙
   1. 브랜치명: 내용
   2. ex) S08P31A408-124: 이런이런 기능
2. 기본 규칙
   1. merge는 혹시모를 문제를 막기위해 팀원의 확인을 받고 올립니다.
   2. 확인한 팀원은 **리뷰**를 남기거나 확인했음을 **이모지**로 남겨줍니다.
   3. 소스코드의 오류를 머지 이전에 발견할 수 있습니다.

### 브랜치

1. Master
2. Develop
   1. BE
      - Feature1 (기능 단위로 나누기)
      - Feature2
      - …
3. Release
4. Hotfix

## Jira

### Issue

에픽, 스토리만 사용

### Label (대분류)

기획, 학습, BE, FE, CI/CD 중 선택해서 작성

### Epic

기획, 학습, BE, FE, CI/CD 등 기능 단위로 작성

ex) BE - 유저 컨트롤러

ex) BE - 기업 분석 알고리즘 구현

ex) FE - 그룹 선택 화면 구현

### Story

[Label] 스토리

ex) [BE] 회원 정보 수정 api 구현

ex) [BE] 베이지안 알고리즘 코드화

ex) [FE] 카카오 로그인 api 구현

### 우선 순위

할일의 우선 순위를 나누어 다섯 단계로 정확히 분류

### Stroy point

하루 8시간을 story 별로 나누기

story를 잘 예측하여 진행 시간 세분화 하기

## FE

### 코틀린 공식 - ktlint<br/>

## BE

### 파일

- 파스칼 케이스 사용

### 변수

- 카멜 케이스 사용
- 의미 없는 변수명 금지
- 누구나 알법한 단어 제외 줄이지 말기

### 메서드

- 카멜 케이스 사용
- create / read / update / delete 형식으로 메서드 이름 쓰기
- **메서드는 하나의 기능만 수행**

### DTO 네이밍

- 비즈니스 로직과 관련된 이름으로 정하기
- 데이터 입력을 받을 경우 이름에 InDTO
- 데이터 출력을 보낼 경우 이름에 OutDTO
- 앞은 대문자, 끝은 DTO로 마무리

### REST API URL

- RESTful하게 쓰기
- URL 끝은 항상 명사

### 주석

- 컨트롤러에서 API 단위로 메서드 만들면 Swagger 명세 꼭 적어주기
- 컨트롤러 외에는 Class 위, 메서드 위에 문단 주석으로 설명하기
  - 카멜 케이스
  - 줄임말 지양 ex) cnt (X) count (O)

## 팀원 역할 분배

AD : 고은빈, 이창준

BE : 김태현, 조원재, 추호성

CI/CD : 김태현

기획/알고리즘 : 정석진

## 프로젝트 산출물

- [아키텍처](./DOCS/architecture)
- [와이어프레임](./DOCS/wireframe)
- [API](./DOCS/api)
- [ERD](./DOCS/erd)

## 프로젝트 결과물

- [포팅메뉴얼](./exec)
- [최종발표자료](./DOCS/presentation/doerandoeran_presentation.pdf)
