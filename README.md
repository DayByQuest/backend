<div style="text-align: center;">

<h2> DayByQuest Backend Repository </h2>

**DayByQuest**는 과시하는 문화의 SNS에서 벗어나 일상의 소소한 퀘스트를 통해 건강함을 추구하는 SNS 서비스입니다.


</div>

## 소개

Java17, SpringBoot 3.1.2를 사용하는 백엔드 서버 프로젝트입니다.

## 실행

- 도커 컴포즈 사용

```shell
$ ./gradlew bootBuildImage

$ cd ./docker

$ docker-compose up
```

- CLI 사용 (로컬스택 실행을 위해 도커가 필요합니다)

```shell
$ ./gradlew build

$ export DB_URL=...
$ export DB_USERNAME=...
$ export DB_PASSWORD=...

$ cd docker
$ docker-compose up localstack -d
$ cd ..

$ java -jar ./build/libs/dbq-0.0.1-SNAPSHOT.jar
```