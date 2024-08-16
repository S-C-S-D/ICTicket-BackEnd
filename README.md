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
### 분산 락, 낙관적 락
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

<br>
<!-- ====================== Infra ====================== -->

### Infra
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
  


<br>
<!-- ====================== Mysql ====================== -->

### MySQL

<details>
<summary><strong> 왜 MySQL을 사용했을까? </strong></summary>
 
티켓팅 서비스는 **데이터의 무결성이 중요한 서비스**이므로 트랜잭션 ACID 속성을 통해서 안전한 데이터 처리를 하기 위해 rdbms를 선택하였습니다. 

수많은 공연을 조건에 따라 조회를 해야하는 부분이 많았는데, MySQL은 고성능의 쿼리 처리 능력을 가지고 있어 대량의 데이터 처리와 빠른 응답 속도를 제공하고, 이는 티켓팅 서비스의 사용자 경험을 향상시키는 데 적합하다고 판단하였습니다.

마지막으로 MySQL은 기본적으로 무료, 속도와 안정성, 보안까지 좋아서 선택했고, 팀원들이 이미 MySQL을 사용해본 경험이 있어, 단기간 내에 개발 효율성을 극대화할 수 있습니다.
</details>

<br>


## 🛠 트러블슈팅

### 동시성 제어에서 Lock을 걸때 Transactional이 걸리지 않는 문제

분산락을 통하여 좌석 예매 처리를 할 때, 락을 획득한 사람이 좌석 예매 로직을 수행하면, 
좌석 테이블의 상태 값이 업데이트가 되어야 하는데, 업데이트가 되지 않는 문제가 발생하였습니다.

<details>
<summary><strong> 1. Transaction이 왜 안걸렸을까? </strong></summary>

 <img src="https://github.com/user-attachments/assets/af99ee2a-f6e9-4a38-ad1b-6b08fe4709d9" width="700px">
 
 > Lock을 획득하는 메서드 = `OutterMethod()`
 > 
 > @Transactional 이 걸려있는 좌석 예매 상태 값을 수정하는 메서드 = `InnerMethod()`


`OutterMethod()`를 통해 Lock을 얻고, `InnerMethod()` 가 `OutterMethod()` 안에서 락을 획 득 후
호출되어 좌석 선택 로직을 수행 한 뒤,Transaction을 통해 DB를 업데이트 하고 나서 다시 Lock을 반납해야 하는데, Transaction이 걸려있는 메서드의 클래스는 **프록시 객체로 bean에 등록이 됩니다.**

Transaction을 수행하기 위해서는 이 프록시 객체를 거쳐야 수행이 되는데, 
`OutterMethod()`를 통해 `InnerMethod()`를 호출한 경우, 프록시 객체에  등록되어있는 `InnerMethod()`가 아닌, `일반 객체의 InnerMethod()` 가 호출돼서 transactional이 걸리지 않는 현상 발생했습니다.
</details>


<details>
<summary><strong> 2. 첫번 째 해결방법 논의 - .save() 활용하기 (실패) </strong></summary>

 간편하게 .save() 메서드를 통하여 DB를 업데이트 해주면 될 것으로 보이지만, 
 여러 개의 좌석을 선택했을 때 문제가 발생하였습니다. 
 
 <img src="https://github.com/user-attachments/assets/1d14277e-e071-4ce1-9484-00d50d03ed55" width="700px">

만약, transaction가 되지 않는 걸 무시하고, .save()를 활용하여 작업을 처리했을 때,
유저 A가 선택한 좌석 1번과 2번의 좌석 상태 값은 .save()를 통하여 DB에 저장이 되어버리고,
transaction 롤백이 되지 않아, 결과적으로 정상적인 로직 처리가 불가능하다는 걸 깨달았습니다.
</details>


<details>
<summary><strong> 3. 두번 째 해결방법 논의 - Aop 활용하기 (성공) </strong></summary>

 <img src="https://github.com/user-attachments/assets/2102f446-cc9e-401a-8803-4d161ec0176a" width="700px">

`OutterMethod()`와 `innerMethod()` 를 분리 후, 
Aop를 활용하여, seatService의 `innerMethod()`가 호출되기 전에

```java
Aop가 실행되어 Lock을 획득 → @Transactional이 걸려있는 OutterMethod()를 호출 → 
innerMethod() 의 로직 수행 → DB업데이트를 수행 → 
@Transactional이 끝난 이후에 Lock을 해제
```

정말 긴 논의 끝에 위와 같은 방법으로 해결 할 수 있다는 리소스를 얻고 진행해본 결과, 
정상적으로 락을 획득한 후에 Transaction을 통하여 로직이 수행되는 결과를 얻을 수 있었습니다.
 
</details>


<br>


### Security 와 관련이 없는 상황의 예외가 AuthenticationEnrtyPoint에서 잡히는 문제

잘못된 url을 보낸경우, requestDto에서 JsonParse를 할 때, LocalDate 형식이 맞지 않는 경우, 
enum에 맞게 보내야하는데 그렇게 보내지 않았을 경우, JsonParse Exception이 발생해야 하는데, AuthenticationEnrtyPoint 에서 설정해둔 Exception이 캐치되는 문제가 발생했습니다.


<details>
<summary><strong> 1. 왜 AuthenticationEnrtyPoint에서 Exception을 캐치했을까? </strong></summary>

 위와 같은 상황에서 Exception이 발생한 경우, 
 스프링 부트에서는 404 에러 혹은 400 에러를 발생시키면서 동시에 “/error” 경로로 
 redirect를 시키게 됩니다. 이때, SecurityConfig에 “/error” url에 대한 접근 허용 설정을 
 하지 않았기 때문에 인증 절차가 요구되면서 시큐리티가 403 에러를 발생시키게되고 이를 AuthenticationEnrtyPoint에서 캐치하면서 의도하지 않은 곳에서 exception 처리가 됩니다.
 <img width="2408" alt="Exception" src="https://github.com/user-attachments/assets/ed9493b0-4177-4ce6-9581-19e07b4f6ba3">
 
</details>


<details>
<summary><strong> 2. 해결 방법 </strong></summary>

먼저 “/error” url에 접근 권한을 설정해주어서 403 에러가 발생하는 것을 막아주었고, 
자주 발생할 수 있는 HttpMessageNotReadableException을 ExceptionHandler로 
추가해주어 따로 exception을 관리할 수 있게 해주었습니다. 
 
</details>

<br>


### void메서드에 관한 TestCode 작성시 해당 메서드의 객체를 제대로 검증하기 힘든 문제

void 메서드의 경우 반환 값이 존재하지 않아 테스트 대상 메서드 내부의 객체를 테스트 코드로 전달하기 어렵다는 문제점이 있었습니다.

<details>
<summary><strong> 1. 왜 ArguementCaptor를 사용했을까 </strong></summary>

 ArgumentCaptor는 Mockito와 같은 mocking 프레임워크에서 사용되는 유틸리티로, 테스트 중에 특정 메서드가 호출될 때 그 메서드의 인자로 전달된 값을 캡처(capture)하고, 이를 통해 테스트를 좀 더 정밀하게 할 수 있게 도와줍니다.
 
</details>

<details>
<summary><strong> 2. 해결 방법 </strong></summary>

ArguementCaptor<T> 객체를 사용하여 void 메서드 내부에서 사용하는 메서드의 매개변수를 캡쳐한 뒤 ArguementCaptor 객체에서 getValue() 메서드를 호출한 후 검증하고자 하는 객체를 가져옵니다. 그 다음, assertEquals 메서드를 이용하여 검증하고자 하는 객체를 활용한 테스트 코드를 작성하였습니다.

```java
public void createLike(Long performanceId, User loginUser) {    
	...  
	likeRepository.save(saveLike);  // saveLike 는 Like 객체이다.
}
```

```java
@Test
void createLike(){
	//given
	Long userId =1;
	
	...
	
	//then
	ArgumentCaptor<Like> captor = ArgumentCaptor.*forClass*(Like.class); // 낚아채고자하는 객체를 제네릭으로 선언
	*verify*(likeRepository).save(captor.capture()); // save 메서드가 호출될때 사용된 인자(parameter)를 캡쳐
	Like savedLike = captor.getValue();  
	*assertEquals*(userId, savedLike.getUser().getId()); // 캡쳐한 객체를 활용하여 테스트 진행
}
``` 
</details>

<br>

### Redis 분산 락 Key 설정

<details>
<summary><strong> 설명 </strong></summary>

처음 분산 락을 적용했을 때, 락 획득에 필요한 key를 하나로 설정을 했었습니다. 이는 Seat 테이블 자체에 락을 거는 느낌으로, 각기 다른 좌석을 예매하려고 하는 모든 요청들이 하나의 key를 기다리는 상황이 되어 효율적이지 못하다 판단했습니다.

이를 해결하기 위해 각각의 row별로 key를 생성해서 관리할 수 있도록 설정하고자 했습니다. 하지만, 요구사항상 한번에 여러 개의 좌석을 예매해야 하는 상황에서 구조적으로 이를 구현하는데 어려움이 있었습니다. 락을 먼저 획득하고, 트랜잭션이 실행되어서 한번에 여러 개 좌석이 예약되거나 롤백 되도록 해야하기 때문입니다.

결국, 좌석별이 아닌, 각 세션별로 키를 생성하여 관리하도록 설정했습니다. 즉, 공연의 회차별로 락이 걸려서, 회차별 좌석 예매요청들끼리 key를 기다리도록 설정해 효율을 높였습니다.
 
</details>






