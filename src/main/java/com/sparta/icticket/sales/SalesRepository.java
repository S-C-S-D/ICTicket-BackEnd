package com.sparta.icticket.sales;

import com.sparta.icticket.performance.Performance;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales,Long> {

    @Query("select s from Sales s join fetch s.performance where s.performance = :findPerformance")
    Optional<Sales> findByPerformance(Performance findPerformance);

    @Query("select s from Sales s join fetch s.performance where s.id = :salesId and s.performance = :performance")
    Optional<Sales> findByIdAndPerformance(Long salesId, Performance performance);

    boolean existsByPerformance(Performance findPerformance);
}