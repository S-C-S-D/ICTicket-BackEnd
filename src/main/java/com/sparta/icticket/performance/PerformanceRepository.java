package com.sparta.icticket.performance;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceRepositoryQuery {

    // 단일 공연 조회, 낙관적 락 적용
    @Override
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Performance> findById(Long id);
}