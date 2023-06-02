# 도란도란 포팅 메뉴얼

생성 일시: 2023년 5월 18일 오후 2:00<br>
생성자: 김태현<br>
최종 편집 일시: 2023년 5월 19일 오전 11:00<br>
최종 편집자: 김태현<br>

## 목차

1. 환경 설정
2. 포팅 순서
3. 주의 사항
4. 부가설명

## 1. 환경 설정

- 하드웨어 정보
  - Architecture: x86_64
  - CPU Model: Intel(R) Xeon(R) CPU E5-2686 v4 @ 2.30GHz
  - CPU family: 6
  - CPU op-mode(s): 32-bit, 64-bit
  - Socket(s): 1
  - Core(s) per socket: 4
  - Thread(s) per core: 1
  - CPU(s): 4
  - CPU MHz: 2300.167
- 소프트웨어 정보
  - OS : Ubuntu 20.04 LTS focal
  - 배포에 필요한 프로그램 : Docker version 20.10.12, build 20.10.12-0ubuntu2~20.04.1
  - 패키지 관리자 : apt 2.0.2ubuntu0.2 (amd64)

## 2. 포팅 순서

### 1.docker, docker-compose 설치

도커를 사용하여 서비스를 배포하기 때문에 프레임워크, 라이브러리 등 추가 설치 할 필요 없습니다.

여러 도커 컨테이너를 관리하는데 편리한 docker-compose를 사용해서 프로젝트를 배포합니다.

1. 리눅스 패키지 업데이트

   ```
   $ sudo apt update
   ```

2. Docker 설치를 위한 패키지 설치

   ```
   $ sudo apt install apt-transport-https ca-certificates curl gnupg lsb-release
   ```

3. Docker 공식 GPG키 추가

   ```
   $ curl -fsSL <https://download.docker.com/linux/ubuntu/gpg> | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
   ```

4. Docker 저장소 추가

   ```
   $ echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] <https://download.docker.com/linux/ubuntu> $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   ```

5. Docker 설치

   ```
   $ sudo apt update
   $ sudo apt install docker-ce docker-ce-cli containerd.io
   ```

6. Docker 서비스 실행

   ```
   $ sudo systemctl start docker
   $ sudo systemctl enable docker
   ```

7. Docker 버전 확인

   ```
   $ docker --version
   ```

8. Docker Compose 설치

   ```
   $ sudo curl -L "<https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$>(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
   $ sudo chmod +x /usr/local/bin/docker-compose
   $ docker-compose --version
   ```

   ```
   $ sudo curl -L "<https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$>(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
   $ sudo chmod +x /usr/local/bin/docker-compose
   $ docker-compose --version
   ```

### 2. Certbot 초기 인증서 발급 방법

1. 도메인 구매후 Nginx app.conf의 server_name을 구매한 도메인으로 변경시켜준다.
2. docker-compose.yml 의 certbot command 주석 해제, entrypoint를 주석 하고 실행해서 인증서를 발급받는다. (Let's Encrypt의 알림을 받을 본인 이메일을 입력한다)

```
certbot:
    image: certbot/certbot
    container_name: certbot
    restart: unless-stopped
    volumes:
      - ./data/certbot/conf:/etc/letsencrypt
      - ./data/certbot/www:/var/www/certbot
    # entrypoint: "/bin/sh -c 'trap exit TERM; while :; do certbot renew; sleep 12h & wait $${!}; done;'"
    command: certonly --webroot --webroot-path=/var/www/certbot --email {Let's Encrypt의 알림을 받을 이메일 입력} --agree-tos --no-eff-email -d doeran.kr -d www.doeran.kr
```

3. 발급에 성공하면 반대로 entrypoint 주석 해제, command 주석으로 기한 만료시 자동 갱신되도록 변경하는 entrypoint를 실행하게 다시 컨테이너를 실행한다.

### 3. backend src/main/resources/properties 설정

### env.properties

```
# yml 파일에서 환경변수 설정
yml.file.datasource.username = {데이터베이스 비밀번호}
yml.file.datasource.url = jdbc:mysql://{접속주소}/hello?serverTimezone=UTC&characterEncoding=UTF-8
yml.file.datasource.password = {데이터베이스 비밀번호}
yml.file.aws.s3.bucket = {AWS S3 버킷 이름}
yml.file.aws.credentials.accessKey = {IAM accessKey}
yml.file.aws.credentials.secretKey = {IAM secretKey}
jwt.secret = {jwt secretKey (직접생성)}
google.CLIENT_ID = {구글 클라이언트 아이디}
# 1시간 설정 3600000
jwt.access_expiration = {jwt access token 유효기간 설정}
jwt.refresh_expiration = {jwt refresh token 유효기간 설정}
# firebase api key
firebase.api_key = {firebase API Key}
#Server Addr
server_addr = {파이선 api 주소}

# sonarqube
sonarqube_addr = {소나큐브 주소}

sonarqube_token_name = {소나큐브 토큰 이름}
sonarqube_token_secret = {소나큐브 토큰 시크릿 키}
```

### firebase_admin_sdk.json

파이어베이스 프로젝트에서 발행한 sdk 파일

### 5. mysql.env 파일 설정

데이터베이스 생성 후 데이터 베이스 연결을 위한 설정 작성
/database/mysql.env

```
MYSQL_DATABASE=
MYSQL_ROOT_PASSWORD=
MYSQL_USER=
MYSQL_PASSWORD=
```

### 6. docker-compose 실행

다음 명령어를 입력합니다.

```
$ docker-compose up #-d 옵션을 사용하면 로그출력없이 bg에서 실행
```

프로젝트를 중지하고자 한다면 다음 명령어를 수행합니다.

```
$ docker-compose stop # stop대신 down을 사용하면 컨테이너가 중지되고 삭제
```

## 3. 주의 사항

1. docker, docker-compose는 아키텍처, 운영체제 등에 따라 설치 코드가 다를 수 있으니 적절한 버전을 선택하세요.
2. 기본적으로 도커를 실행하려면 root계정 권한이 필요합니다. 일반 계정에서 도커를 사용하고자 한다면 도커를 사용할 수 있는 그룹에 사용자를 추가해야 합니다. 그룹에 사용자를 추가하고자 한다면 다음 명령어를 입력합니다.

```
$ sudo usermod -aG docker 유저계정
```

1. 도커 설정 등에 의해서 Permission definded 에러가 나온다면 다음 명령어를 프로젝트에 적용하세요.

```
$ chown -R 사용자계정 프로젝트root폴더
```

## 4. 기술 스택 & 개발환경

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

## 5. 프로젝트 구조

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
