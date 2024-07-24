package com.sparta.icticket.sales;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales,Long> {
    Optional<Sales> findByPerformance(Performance findPerformance);
}