package com.sparta.icticket.performance;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceRepositoryQuery {

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select p from Performance p join fetch p.venue where p.id =: id")
    Optional<Performance> findById(@Param("id") Long id);
}