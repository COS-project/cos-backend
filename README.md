# 📚 Cercat 핵심 기능

### 🌟 모의고사

회원님은 다양한 년도와 회차에 걸친 모의고사에 응시할 수 있습니다. <br>
각 시험마다 과목별 정답률과 문제별 머문 시간 등의 상세한 분석을 제공하여, 회원님의 학습 진단 및 개선에 필요한 데이터를 제공합니다.

### 📈 맞춤형 학습 관리 시스템

목표 설정 기능을 통해 학습 목표를 세우고,
지난 모의고사 성적을 주간/월간/연간 단위로 통계 내어 성장 과정을 한눈에 확인할 수 있습니다. <br>
이를 통해 학습 방향을 조정하고, 목표 달성을 위한 전략을 세울 수 있습니다.

### 🌐 자격증 커뮤니티

회원님은 각 자격증별 꿀팁, 해설, 자유 게시판을 통해 팁을 공유하고, 다른회원과 소통할 수 있습니다. <br>
다양한 경험과 지식이 모이는 곳에서, 회원님만의 학습 네트워크를 확장해 보세요.

### 🛎️ 맞춤 알림 서비스

회원님이 설정한 관심 자격증에 대한 시험 신청일 및 마감일 알림은 물론, 좋아요 및 댓글 알림까지 <br>
중요한 정보를 놓치지 않고, 커뮤니티 내에서의 소통도 놓치지 않을 수 있도록 알림 서비스를 제공합니다.

# 화면

<div style="display: flex; justify-content: space-between;">
    <img src="https://github.com/COS-project/cos-backend/assets/128073698/d0c21dae-bbaf-4951-a688-174eae59ff20" width="150" height="300">
    <img src="https://github.com/COS-project/cos-backend/assets/128073698/06e9c376-1d41-497a-aa2b-a8765f578848" width="150" height="300">
    <img src="https://github.com/COS-project/cos-backend/assets/128073698/5f6062ea-ffb0-4ddb-bdd9-aed2a7d530c7" width="150" height="300">
<div>
<div style="display: flex; justify-content: space-between;">
    <img src="https://github.com/COS-project/cos-backend/assets/128073698/09dd3501-5501-45b7-b26b-d44eeb26f3ba" width="150" height="300">
    <img src="https://github.com/COS-project/cos-backend/assets/128073698/f2d1af03-6980-44b6-b9f1-96a9a69b56be" width="150" height="300">
    <img src="https://github.com/COS-project/cos-backend/assets/128073698/e4a6c5cb-4012-4444-afa7-c3890327ff87" width="150" height="300">
</div>

# 기술 스택

- 백엔드 : Spring Boot, MySQL, Redis, Kafka, ELK
- 클라우드 : GCP VM, GCS, Docker
- CI/CD : Git Actions, Docker Compose
- 협업도구 : Slack

# 인프라 구성

<img width="1061" alt="image" src="https://github.com/user-attachments/assets/9794c868-98a3-4cfb-905b-fb537b59b7f3" />



- Nginx로 리버스 프록시
- Docker compose로 서버 scale-out
- Kafka를 통해 비동기 알림
- Redis를 통해 검색어 시스템 및 모의고사 문제 데이터 캐싱
- Grafana,Loki,Prometheus를 통해 모니터링 시스템 구축

# CI/CD

![image](https://github.com/user-attachments/assets/6fa98738-f167-4f18-b64c-b9e3115bd9e9)

- 블루-그린 배포방식으로 무중단 배포 구현

# ERD

![image](https://github.com/COS-project/cos-backend/assets/128073698/f8fa127d-db6f-40ae-8e51-fa44f9c79734)

# 아키텍쳐

![image](https://github.com/COS-project/cos-backend/assets/128073698/de0f406e-4b73-477f-bba3-f6caaaead435)

[지속성장가능한 소프트웨어 개발하는 방법](https://geminikims.medium.com/%EC%A7%80%EC%86%8D-%EC%84%B1%EC%9E%A5-%EA%B0%80%EB%8A%A5%ED%95%9C-%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EA%B0%80%EB%8A%94-%EB%B0%A9%EB%B2%95-97844c5dab63)

# 멀티모듈 구조

Cercat 프로젝트는 단일 모듈 구조에서 시작하여, 코드 간의 의존도와 결합도를 줄여보고자 멀티모듈 구조의 프로젝트로 리팩토링을 진행했습니다.

```
📁 applications # Runnable
📁 supports # 공통
📁 core-domain # Business-Layer, Implementation-Layer
📁 infrastructure # Data-Access-Layer, GCS, Kafka, Redis
```

- [applications](https://github.com/COS-project/cos-backend/blob/main/applications/README.md)
- [supports](https://github.com/COS-project/cos-backend/blob/main/supports/README.md)
- [core-domain](https://github.com/COS-project/cos-backend/blob/main/core-domain/README.md)
- [infrastructure](https://github.com/COS-project/cos-backend/blob/main/infrastructure/README.md)

# 패키지 구조

```
com
 ㄴ cos
    ㄴ cercat
        ㄴ post
        ㄴ user
        ...
```
