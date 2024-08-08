package com.sparta.icticket.sales;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales,Long> {

    // performance를 통해 할인 객체 조회
    Optional<Sales> findByPerformance(Performance findPerformance);

    // 할인 id와 performance를 통해 할인 객체 조회
    Optional<Sales> findByIdAndPerformance(Long salesId, Performance performance);

    // findByperformance와 같은 역할이라 삭제 필요
    Optional<Sales> findDiscountRateByPerformance(Performance performance);

    // 해당 performance를 가진 할인 객체 존재 여부를 조회
    boolean existsByPerformance(Performance findPerformance);
}