























![ICTicket](https://github.com/user-attachments/assets/17d47ff2-5021-4921-bef5-2458dfafd6aa)
```
IC티켓은 고속도로의 'IC'를 의미하며, 
이름 그대로 ‘고속도로처럼 빠르게 티켓을 예매할 수 있다’ 는 뜻을 담고 있습니다.
```
<br>

## 💎 서비스 페이지
[ICTicket](https://icticket.shop/)

<br>

## 💎 팀 노션
[ICTicket Notion](https://www.notion.so/IC-Ticket-db0367a7efb14fd6a1ff3233f3abb8a7)

<br>

## 💁🏻‍♂️ 팀 멤버

|[<img src="https://github.com/YeahyunKim.png" width="100px">](https://github.com/YeahyunKim)|[<img src="https://github.com/jay1261.png" width="100px">](https://github.com/jay1261)|[<img src="https://github.com/Lee0zoo.png" width="100px">](https://github.com/Lee0zoo)|[<img src="https://github.com/dolee2403.png" width="100px">](https://github.com/dolee2403)|[<img src="https://github.com/hangeunhyeong.png" width="100px">](https://github.com/hangeunhyeong)|
|:----:|:----:|:----:|:----:|:----:|
|[김예현](https://github.com/MK827)|[이동재](https://github.com/jay1261)|[이영주](https://github.com/youihyonKim)|[이도](https://github.com/bulmang)|[한근형](https://github.com/bokoo14)|
|[Blog](https://maltyy.tistory.com/)|[Blog](https://jay1261.github.io/)|[Blog](https://velog.io/@leeyj4515/posts)|[Blog](https://blog.naver.com/little-561)|[Blog](https://9ny3ong.tistory.com/)|

<br>

## 💎 프로젝트 목표

이번 티켓팅 서비스 프로젝트를 진행하게 된 이유는 백엔드 개발자로서 갖춰야 할 여러 가지 기능들을 깊이 있는 학습을 통해 경험을 쌓을 수 있다고 생각했기 때문입니다. 

**`다양한 조건으로 공연 조회 기능`**
JPA와 JPQL, QueryDSL을 활용하여 사용자가 원하는 공연을 다양한 조건으로 효율적으로 검색할 수 있는 기능을 구현했습니다. 이를 통해 쿼리 최적화의 중요성을 깊이 이해하게 되었습니다.

**`좌석별 금액 및 공연별 할인율 설정`**
복잡한 ERD 구조를 해결하고 최적화하는 과정을 통해 데이터베이스 설계의 중요성을 깨달았습니다. 이를 통해 시스템의 성능을 향상시키고, 유지보수의 용이성을 확보했습니다.

**`좌석 독점 방지 및 동시성 처리`**
사용자가 결제를 진행하지 않아도 좌석을 독점할 수 없도록 처리하기 위해 @Scheduler를 활용하여 ERD 구조를 고민했습니다. 또한, 하나의 좌석에 대해 여러 요청이 동시에 들어오는 상황을 효과적으로 관리하기 위해 Redis를 사용한 동시성 제어를 구현했습니다.

이 프로젝트를 통해 단순한 기능 구현을 넘어서, **실제 서비스 환경에서 발생할 수 있는 여러 문제를 해결하는 방법에 대해 팀원들과 심도 깊은 논의를 진행**했습니다. "어떻게 하면 유저들이 혼란을 겪지 않도록 할 수 있을까?"라는 질문을 바탕으로 사용자 경험을 고려한 설계를 고민하며, 백엔드 개발자로서의 전문성을 한층 더 발전시킬 수 있었습니다.


<br>

## 🏗 아키텍쳐
![아키텍쳐](https://github.com/user-attachments/assets/dc8d072e-2550-4a3b-92b4-1243bcebb724)

<br>

## 🔥 개발 환경

### Backend

- Java  17
- SpringBoot  3.27.x
- MySQL  8.0.x
- Redis 7.4.0

### Infra

- EC2
- Route53
- Docker  27.1.1
- Docker Compose  2.29.1
- Github Action
- NGINX  (WAS, SSL, Reverse Proxy)

### Frontend

- Vanila JS
- node.js 22.4.x
- npm 10.8.x

<br>

## 🍀 주요 기술
### 리버스 프록시

리버스 프록시를 활용하여 프론트 서버 앞단에 Nginx를 배치하여 웹 애플리케이션의 성능과 안정성 향상

### CI/CD 환경 구축

Github Actions를 이용한 CI 테스트 환경 및 Docker를 활용한 CD 파이프라인 구축

### 좌석 예매 / 조회수 동시성 제어

분산락(Distributed Lock), 낙관적 락(Optimistic Lock)을 활용

### 좌석 장시간 독점 방지

`seat` 테이블의 `seat_status` / `user_id` 컬럼으로 유저가 좌석을 선택했을 때, 10분 이내에 
결제하지 않은 경우 다시 `seat_status` 값과 `user_id` 값을 초기화 해주는 로직을 구현하였습니다.

만약 유저가 결제하는 페이지에서 어떠한 액션도 취하지 않고 대기하여 좌석을 독점할 경우에는
 `@Scheduled` 가 5분에 1번씩 DB를 조회하여, `seat`테이블의 `reservedAt` 기준으로 5분이 지난 좌석의 경우에는 다시 `seat_status` 값을 초기화 해주는 로직을 구현하였습니다.

<br>

## 🗣️ 기술적 의사결정

<!-- ====================== 분산 락, 낙관적 락 ====================== -->

<details>
<summary><strong> 분산 락, 낙관적 락 </strong></summary>

  <details>
  <summary><strong> 1. 분산 락 </strong></summary>
  
**좌석 예매하기에 왜 분산락을 사용했을까?**

사용자가 좌석을 예매할 때, 동일한 좌석을 동시에 선택하는 경우 두 사용자 모두 예매에 성공하는 문제가 발생했습니다. 이를 해결하기 위해 동시성 제어를 적용하게 되었습니다.

좌석 예매 요청은 충돌이 자주 발생하는 서비스로 판단되므로, 낙관적 락을 사용할 경우 재시도가 빈번해져 성능 저하가 우려됩니다. 따라서 낙관적 락은 제외하였습니다.

비관적 락은 적용이 비교적 용이하고 동시성 보장을 잘 하지만, 데이터베이스(DB) 자체에 락을 설정하는 쿼리가 발생하기 때문에 효율성 면에서 부족하다고 판단하여 이 역시 제외하였습니다.

결국 분산 락 사용을 결정하게 되었으며, 이는 비관적 락이나 낙관적 락과 달리 DB에서 락을 설정하지 않고 다른 임계 영역에서 락을 관리합니다.

분산 락은 MySQL과 Redis 등에서 구현할 수 있지만, MySQL은 DB에서 Lock을 관리하게 되어 부담이 되는 단점이 있습니다. 따라서 Redis를 선택하게 되었습니다.

Redis 분산 락 구현에는 Lettuce와 Redisson 두 가지 옵션이 있습니다. Lettuce는 Spin Lock 알고리즘을 사용하여 Redis 서버에 지속적으로 락 획득 요청을 보내는 방식입니다. 반면, Redisson은 RedLock 알고리즘을 사용하여 락 획득과 해제를 pub-sub 구조로 수행하여 성능적으로 우수합니다. 이에 따라 Redisson을 이용해 분산 락을 구현하기로 하였습니다.

**적용 결과**

결과적으로 100명이 한번에 하나의 좌석을 예매하는 요청을 보냈을 때, 약 10명이 좌석 예매를 성공하는 문제가 분산 락을 적용한 후 오직 1명만 좌석 예매에 성공하게 되었습니다.  
  </details>

  <details>
  <summary><strong> 2. 낙관적 락 </strong></summary>
  
**공연 조회수에 왜 낙관적 락을 사용했을까?**

공연 조회수를 올리는 로직에서 여러 사용자가 동시에 조회할 경우, 조회수가 제대로 반영되지 않는 문제가 발생했습니다. 조회수는 서비스에서 중요한 데이터는 아니며, 동시에 많은 요청이 발생하지 않는 특성을 고려하여 낙관적 락을 적용하기로 하였습니다. 재시도 횟수는 10회로 설정하였습니다.

**적용 결과**

결과적으로, 100회의 요청이 동시에 들어왔을 때 처음에는 40~50%의 요청만이 반영되었으나, 
이후 약 90%의 요청이 성공적으로 반영되는 성과를 얻었습니다.  
  </details>
</details>

<br>
<!-- ====================== Infra ====================== -->

<details>
<summary><strong> Infra </strong></summary>

  <details>
  <summary><strong> AWS(EC2) </strong></summary>
  
- AWS EC2는 필요에 따라 인스턴스를 쉽게 생성하고 삭제할 수 있는 유연성을 제공합니다. 이는 트래픽 변화에 따라 자원을 적절히 조정할 수 있어, 특히 이벤트 기반의 티켓팅 서비스에서 중요하다는 생각이 들었습니다.
- EC2는 수요에 따라 자동으로 인스턴스를 추가하거나 제거하는 오토 스케일링 기능을 지하는데, 이 기능을 활용하여 유명한 공연의 티켓팅이 시작한 시간대에 사용자 요청이 급증할 때 원활한 서비스를 제공할 수 있습니다.
  
  </details>

  <details>
  <summary><strong> Github Action </strong></summary>
    
  - ‘Jenkins와 Github Action중 어떤것을 사용하는게 우리 프로젝트에 적합할까?’ 라는 고민에 빠져있었는데, 현재 우리 프로젝트는 한대의 서버만 가지고 있고, 프로젝트 기간 내에 CI/CD를 구축하기 위해서는 Github Action이 더 적합하겠다라는 생각이 들었습니다.
  - 또한 PR을 하는 과정에서 테스트코드가 제대로 작동되는지 확인할 수 있다는 것이, 프로젝트에 배포하는 과정에서 런타임 오류와 같은 이슈를 줄이는데 큰 도움이 되었기 때문에, Github Action을 적용했습니다.

  </details>

  <details>
  <summary><strong> Docker </strong></summary>
    
    - Docker 이미지는 다양한 환경에서 동일하게 실행될 수 있으므로, 개발 환경과 프로덕션 환경의 일관성을 유지하는데 유리하여 적용하게 되었습니다.
    - 또한 Github Action을 통해서 Docker Hub에 이미지를 푸시하고 Docker Compose를 사용해 서버에 배포하는 편의성이 있어서 Docker 적용여부를 쉽게 결정할 수 있었습니다.
  
  </details>
  
</details>

<br>
<!-- ====================== Mysql ====================== -->

<details>
<summary><strong> 토글 제목 </strong></summary>

  <details>
  <summary><strong> 토글 제목 </strong></summary>
  
  여기에 토글 내용이 들어갑니다. 이 부분은 토글이 열릴 때만 보입니다.
  
  </details>

  <details>
  <summary><strong> 토글 제목 </strong></summary>
  
  여기에 토글 내용이 들어갑니다. 이 부분은 토글이 열릴 때만 보입니다.
  
  </details>
</details>

