# 📚 Cercat 핵심 기능
### 🌟 모의고사
#### 다양한 시험:
회원님은 다양한 년도와 회차에 걸친 모의고사에 응시할 수 있습니다. <br>
각 시험마다 과목별 정답률과 문제별 머문 시간 등의 상세한 분석을 제공하여, 회원님의 학습 진단 및 개선에 필요한 데이터를 제공합니다.  
### 📈 맞춤형 학습 관리 시스템
#### 목표 설정과 성과 추적: 
목표 설정 기능을 통해 학습 목표를 세우고,
지난 모의고사 성적을 주간/월간/연간 단위로 통계 내어 성장 과정을 한눈에 확인할 수 있습니다. <br>
이를 통해 학습 방향을 조정하고, 목표 달성을 위한 전략을 세울 수 있습니다.
### 🌐 자격증 커뮤니티
#### 지식 공유와 소통의 장: 
회원님은 각 자격증별 꿀팁, 해설, 자유 게시판을 통해 팁을 공유하고, 다른회원과 소통할 수 있습니다. <br>
다양한 경험과 지식이 모이는 곳에서, 회원님만의 학습 네트워크를 확장해 보세요.
### 🛎️ 맞춤 알림 서비스
#### 중요 정보, 놓치지 마세요: 
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
- 백엔드 : Spring Boot, Redis, Kafka, ELK, Docker
- 클라우드 : GCP VM, GCS, GCP MySQL
- CI/CD : Git Actions, Docker Compose
- 협업도구 : Slack

# 인프라 구성
<img width="1013" alt="cercat 아키텍쳐" src="https://github.com/COS-project/cos-backend/assets/128073698/14d2c909-676d-4419-b090-72b6d3a9870b">

- Nginx로 리버스 프록시
- Kafka를 통해 비동기 알림 pub/sub
- Debezium 커넥터를 통해 MySQL의 데이터변경을 캡쳐하여 Kafka로 발행 -> Elastic Search 저장
- Elastic Search의 ngram, nori 분석기등을 통해 검색 최적화
- Redis를 통해 최근 검색기록 및 모의고사 문제 데이터 캐싱
- Git Actions의 Cron job을 통해 Batch 서버 실행
# CI/CD
<img width="1015" alt="cercat-cicd" src="https://github.com/COS-project/cos-backend/assets/128073698/9abc44f9-eace-4dfd-9760-2be7046fc215">


# ERD
![erd](https://github.com/COS-project/cos-backend/assets/128073698/a54221cb-5c76-4b2d-87bd-a816401e6b8d)

# 멀티모듈 구조
```
📁 cercat-application # Controller, Request, Response
📁 cercat-common # 공통
📁 cercat-domain # Service, Implementation
📁 cercat-storage # Entity, Repository
📁 cercat-client # 외부 API 기능(GCP Cloud Storage)
```
- [cercat-application](https://github.com/COS-project/cos-backend/blob/main/cercat-application/README.md)
- [cercat-common](https://github.com/COS-project/cos-backend/blob/main/cercat-common/README.md)
- [cercat-domain](https://github.com/COS-project/cos-backend/blob/main/cercat-domain/README.md)
- [cercat-storage](https://github.com/COS-project/cos-backend/blob/main/cercat-storage/README.md)
- [cercat-client](https://github.com/COS-project/cos-backend/blob/main/cercat-client/README.md)

# 패키지 구조
```
com
 ㄴ cos
    ㄴ cercat
        ㄴ post
        ㄴ user
        ...
```

## 참고한 아키텍쳐

[지속성장가능한 소프트웨어 개발하는 방법](https://geminikims.medium.com/%EC%A7%80%EC%86%8D-%EC%84%B1%EC%9E%A5-%EA%B0%80%EB%8A%A5%ED%95%9C-%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EA%B0%80%EB%8A%94-%EB%B0%A9%EB%B2%95-97844c5dab63)
