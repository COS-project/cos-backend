# 📚 Cercat 핵심 기능
### 🌟 모의고사
#### 다양한 시험:
회원님께서는 다양한 년도와 회차에 걸친 모의고사에 응시하실 수 있습니다. <br>
각 시험마다 과목별 정답률과 문제별 머문 시간 등의 상세한 분석을 제공하여, 회원님의 학습 진단 및 개선에 귀중한 데이터를 제공합니다.  
### 📈 맞춤형 학습 관리 시스템
#### 목표 설정과 성과 추적: 
목표 설정 기능을 통해 학습 목표를 세우고,
지난 모의고사 성적을 주간/월간/연간 단위로 통계 내어 성장 과정을 한눈에 확인할 수 있습니다. <br>
이를 통해 학습 방향을 조정하고, 목표 달성을 위한 전략을 세울 수 있습니다.
### 🌐 자격증 커뮤니티
#### 지식 공유와 소통의 장: 
각 자격증별로 마련된 꿀팁, 해설, 자유 게시판을 통해 회원님은 학습 팁을 공유하고, 동료와 소통할 수 있습니다. <br>
다양한 경험과 지식이 모이는 곳에서, 회원님만의 학습 네트워크를 확장해 보세요.
### 🛎️ 맞춤 알림 서비스
#### 중요 정보, 놓치지 마세요: 
회원님이 설정한 관심 자격증에 대한 시험 신청일 및 마감일 알림은 물론, 좋아요 및 댓글 알림까지 <br>
중요한 정보를 놓치지 않고, 커뮤니티 내에서의 소통도 놓치지 않을 수 있도록 알림 서비스를 제공합니다.

# 기술 스택
- 백엔드 : Spring Boot, Redis, Kafka, Docker, Spring Security
- 클라우드 : GCP VM, GCS, GCP MySQL
- CI/CD : Git Actions, Docker Compose
- 협업도구 : Slack

# 인프라 구성
<img width="1035" alt="cercat-infra" src="https://github.com/COS-project/cos-backend/assets/128073698/26671112-219b-42f5-9f3a-3104ee7737a3">

- Nginx로 리버스 프록시
- Kafka를 통해 비동기 알림 pub/sub
- 수정이 없고 조회가 굉장히 많은 모의고사 문제 데이터 Redis 캐싱
- Git Actions의 Cron job을 통해 Batch 서버 실행
# CI/CD
<img width="1021" alt="cercat-cicd" src="https://github.com/COS-project/cos-backend/assets/128073698/bb6a8975-1660-4ce0-9cad-02fb50db9eac">


# ERD
![erd](https://github.com/COS-project/cos-backend/assets/128073698/a54221cb-5c76-4b2d-87bd-a816401e6b8d)

# 패키지 구조
```
com
 ㄴ example
    ㄴ cos
        ㄴ domain
        |   ㄴ post
        |   | ㄴ api
        |   | ㄴ dto
        |   | ㄴ domain
        |   | ㄴ repository
        |   | ㄴ app
        |   ㄴ user
        |   | ㄴ controller
        |   | ㄴ dto
        |   | ㄴ domain
        |   | ㄴ repository
        |   | ㄴ app
        | ...
        ㄴ global
            ㄴ common
            ㄴ config
            ㄴ exception
            ㄴ security
            ㄴ util
```
