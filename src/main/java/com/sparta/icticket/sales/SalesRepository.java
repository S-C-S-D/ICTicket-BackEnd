package com.sparta.icticket.sales;

import com.sparta.icticket.performance.Performance;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales,Long> {

    Optional<Sales> findByPerformance(Performance findPerformance);

    Optional<Sales> findByIdAndPerformance(Long salesId, Performance performance);

    boolean existsByPerformance(Performance findPerformance);
}