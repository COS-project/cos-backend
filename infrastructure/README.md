## Infrastructure 모듈

`infrastructure` 모듈은 데이터 저장소 및 외부 시스템과의 통신을 담당하는 계층입니다.  
DB, 캐시, 검색 엔진, 외부 API 연동 등 애플리케이션 운영에 필요한 인프라를 구성합니다.

### 포함된 서브모듈

- **`infrastructure:client-gcs`**  
  - Google Cloud Storage(GCS)와의 연동을 위한 클라이언트 모듈입니다.  
  - 파일 업로드, 다운로드 및 관리 기능을 제공합니다.

- **`infrastructure:db-mysql`**  
  - MySQL 데이터베이스와의 연결을 담당하는 모듈입니다.  
  - JPA/Hibernate 설정 등이 포함되어있습니다.

- **`infrastructure:cache-redis`**  
  - Redis 캐시 서버와의 연동을 위한 모듈입니다.  
  - 캐싱 및 속도 향상을 위한 데이터 저장소로 활용됩니다.

- **`infrastructure:search-es`**  
  - Elasticsearch를 활용한 검색 기능을 담당하는 모듈입니다.  
  - 로그 검색, 실시간 데이터 분석 및 고급 쿼리 기능을 지원합니다.

이 모듈들은 개별적인 역할을 수행하면서도 **Infrastructure 계층**에서 관리되어,  
도메인 로직과 분리된 구조를 유지할 수 있도록 설계되었습니다.
