package com.sparta.icticket.performance;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceRepositoryQuery {


    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = "select p from Performance p where p.id =:id")
    Optional<Performance> getByIdCustom(@Param("id") Long id);


}