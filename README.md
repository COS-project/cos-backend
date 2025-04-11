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

# 챌린지

## 🚨 1. 기술 의존성 분리 문제

비즈니스 로직이 JPA, Kafka, Redis 등의 기술에 강하게 결합되어 테스트 작성이 어렵고 코드 가독성이 떨어지는 문제가 있었습니다.

### ⚙️ 해결

1. **도메인과 인프라 계층 분리**
    - 도메인 모듈에서 핵심 비즈니스 로직 구현
    - 외부 기술 의존성을 인터페이스로 추상화
    - 인프라 모듈에서 실제 기술 구현체 개발

### 성과

- **코드 가독성 향상**: 비즈니스 로직과 기술 구현 분리
- **테스트 용이성**: 의존성 주입을 통한 모킹 간소화
- **기술 변경 유연성**: 도메인 로직 수정 없이 기술 변경 가능
- **개발 생산성 향상**: 비즈니스 로직에 집중 가능

### 배운 점

의존성 역전을 통한 기술 구현체의 분리가 도메인 및 비즈니스 로직을 얼마나 자유롭게 만드는지 직접 경험할 수 있었습니다. 기술에 종속되지 않는 코드를 작성함으로써 훨씬 더 유연하고 변경에 강한 시스템을 구축할 수 있다는 것을 배웠습니다.

## 🚨 2. 아키텍처 복잡성과 오버엔지니어링

최신 기술 트렌드를 따라가려는 욕심과 미래의 대규모 트래픽을 과도하게 예측한 설계가 문제였습니다. 실제 서비스 요구사항과 규모에 맞지 않는 복잡한 아키텍처는 개발 및 운영 부담만 가중시켰습니다.

### ⚙️ 해결

1. **아키텍처 단순화**
    - ElasticSearch, Debezium CDC, Logstash 제거 → MySQL 전문 검색으로 대체
    - 실시간 검색어 및 자동완성 기능 → Redis로 전환

### 성과

<div align="center">
  <table>
    <tr>
      <td align="center"><b>개선 전</b><br><img src="https://github.com/user-attachments/assets/c68ca9f2-be91-445c-a00f-005c5a6a5286" width="500"/></td>
      <td align="center" style="font-size: 24px;">➡️</td>
      <td align="center"><b>개선 후</b><br><img src="https://github.com/user-attachments/assets/9794c868-98a3-4cfb-905b-fb537b59b7f3" width="500"/></td>
    </tr>
  </table>
</div>

- **비용 절감**: ES 클러스터 운영 비용 절감
- **장애 포인트 감소**: 시스템 복잡도 60%감소
- **검색 성능 유지**: MySQL 전문검색과 Redis를 통해 유지

### 배운 점

기술 도입은 현재 요구사항과 비즈니스 가치에 기반해야 함을 배웠습니다. 점진적인 확장이 가능한 단순한 설계의 가치를 깨달았습니다.

## 🚨 3. 알림 시스템 성능 및 확장성 문제

API 서버에서 비즈니스 로직과 알림 전송을 동시에 처리해 응답 속도가 저하되고, SSE 방식에만 의존해 FCM, 이메일 등 다른 알림 채널 추가가 어려웠습니다.

### ⚙️ 해결

1. **이벤트 기반 아키텍처 도입**
    - Kafka를 통한 비동기 메시지 처리
    - 알림 채널 인터페이스 추상화로 확장성 확보

### 성과

- **API 응답 시간 단축**: 비동기 처리로 즉시 응답
- **알림 채널 확장**: FCM 채널 추가
- **서비스 안정성 향상**: 알림 서버 장애 시에도 API 서버 정상 작동

### 배운 점

주요 비즈니스 로직과 알림 처리의 관심사 분리를 통해 기존에 내가 OOP(SRP)를 제대로 지키지 않고 있었다는 걸 알 수 있었습니다. 또한 추상화를 통해 다양한 알림 채널을 추가하면서 추상화의 중요성도 다시 한번 느낄 수 있었습니다.

## 🚨 4. 메시지 처리 안정성 이슈

분산 시스템에서 발생할 수 있는 일시적 장애나 네트워크 문제에 대한 대응 메커니즘이 부족했습니다. 특히 장애 상황에서의 재시도 전략과 메시지 중복 처리 방지책이 미흡했습니다.

### ⚙️ 해결

1. **메시지 발행 안정성 강화**
    - 지수 백오프 재시도 전략 구현
    - 실패 시 DB에 저장 후 주기적 재발행
2. **메시지 소비 안정성 강화**
    - 에러 핸들러 통한 재시도 로직
    - Dead Letter Queue로 실패 메시지 보존
    - 이벤트 ID 기반 중복 처리 방지

### 성과

- **메시지 유실률 대폭 감소**: 안정적인 메시지 전달
- **장애 복원력 향상**: 일시적 장애에도 메시지 전달 보장
- **빠른 문제 감지**: DLQ 모니터링 통한 장애 대응

### 배운 점

메시지 큐를 도입함으로써 얻을 수 있는 이점은 분명 크지만, 메시지 큐 자체로 인해 발생할 수 있는 장애나 예외 상황에 대비하는 것이 안정적인 서비스 운영에 있어 더욱 중요하다는 것을 배웠습니다.
