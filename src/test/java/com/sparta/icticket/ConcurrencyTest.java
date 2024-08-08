package com.sparta.icticket;


import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.performance.PerformanceService;
import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.seat.SeatService;
import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
import com.sparta.icticket.seat.dto.SeatReservedResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@SpringBootTest
public class ConcurrencyTest {
    @Autowired
    PerformanceService performanceService;
    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    SeatService seatService;
    @Autowired
    SeatRepository seatRepository;

    long startTime;
    long endTime;

    int times = 100;

    @BeforeEach
    void beforeEach() {
        startTime = System.currentTimeMillis();
    }

    @AfterEach
    void afterEach(){
        endTime = System.currentTimeMillis();
        System.out.println("수행 시간: " + (endTime - startTime));
    }

    @Test
    @DisplayName("분산 락, 좌석 선택 테스트")
    @Transactional
    void test(){
        Random random = new Random();
        IntStream.range(0, times).parallel().forEach(i -> {
            List<Long> idList = new ArrayList<>();
            idList.add(1L);//(random.nextLong(10) + 1L);
            System.out.println("idList = " + idList.get(0));
            SeatReservedRequestDto seatReservedRequestDto = new SeatReservedRequestDto(idList);
            seatService.reserveSeat(1L, seatReservedRequestDto, null);
//            SeatReservedResponseDto seatReservedResponseDto = seatService.redisTest(1L, seatReservedRequestDto);
//            System.out.println("seatReservedResponseDto = " + seatReservedResponseDto.toString());
        });
    }

    @Test
    @Rollback(value = false)
    @DisplayName("분산 락, 좌석 선택 테스트")
    void test2(){
//        Random random = new Random();
        IntStream.range(0, times).parallel().forEach(i -> {
            List<Long> idList = new ArrayList<>();
            idList.add(1L);
            System.out.println("idList = " + idList.get(0));
            SeatReservedRequestDto seatReservedRequestDto = new SeatReservedRequestDto(idList);
//            SeatReservedResponseDto seatReservedResponseDto = seatService.redisTest(1L, seatReservedRequestDto);
//            System.out.println("seatReservedResponseDto = " + seatReservedResponseDto.toString());
        });
    }



//    @Test
//    @DisplayName("락 걸지 않음") // 469
//    void test1(){
//        IntStream.range(0,times).parallel().forEach(i -> {
//            performanceService.noLock();
//        });
//
//        Performance performance = performanceRepository.findById(1L).get();
//        System.out.println("performance.getViewCount() = " + performance.getViewCount());
//    }
//
//    @Test
//    @DisplayName("비관적 락") // 667
//    void test2(){
//        IntStream.range(0,times).parallel().forEach(i -> {
//            performanceService.pessimisticLock(1L);
//        });
//
//        Performance performance = performanceRepository.findById(1L).get();
//        System.out.println("performance.getViewCount() = " + performance.getViewCount());
//    }
//
//
//    @Test
//    @DisplayName("Redis 분산락") // 1667
//    void test3(){
//        IntStream.range(0,times).parallel().forEach(i -> {
//            performanceService.redisTest();
//        });

//
//        Performance performance = performanceRepository.findById(1L).get();
//        System.out.println("performance.getViewCount() = " + performance.getViewCount());
//    }
//
//
//
//    /**
//     * 현재 비관적 락이 속도가 더 잘 나오는 것으로 보여지는데,
//     * 예상으로는 이는 현재 상황이 스케일 아웃된 경우가 아님.
//     * 즉, 현재 환경이 하나의 서버에서 접근하는 경우이고, 내부에서 바로 DB접근을 해서 비관적 락이 더 빠른 상태.
//     * 분산락은 레디스에 접근하는 시간도 걸리기 때문에 느린결과가 나옴
//     * 만약 여러대의 서버에서 접근하는 경우 분산 락이 더 빠를 것 같음
//     */
//
//    /**
//     * 분산 락을 적용하면, 하나의 Low에 대해 줄을 서는건지?  -> 코드적으로 바꿀 수 있음
//     * 아니면 하나의 요청 자체에 대해 줄을 서는건지?
//     *
//     * 비관적 락은 하나의 row에 락을 거는 것
//     */
//
//    @Test
//    @DisplayName("비관적 락 여러개 id")
//    void test4(){
//        Random random = new Random();
//        IntStream.range(0,times).parallel().forEach(i -> {
//            Long randomId = random.nextLong(10) + 1L;
//            performanceService.pessimisticLock(randomId);
//        });
//    }
//
//    @Test
//    @DisplayName("분산 락 - row 별로 lock을 획득하도록 설정") // 650
//    void test5(){
//        Random random = new Random();
//        IntStream.range(0,times).parallel().forEach(i -> {
//            Long randomId = random.nextLong(10) + 1L;
//            performanceService.performanceViewCountUpByIdWithRedis(randomId);
//        });
//    }

}
