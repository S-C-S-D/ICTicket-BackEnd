package com.sparta.icticket.performance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceRepositoryQuery {
}