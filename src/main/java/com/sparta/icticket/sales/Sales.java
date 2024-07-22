package com.sparta.icticket.sales;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Sales extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Column(nullable = false)
    private Integer discountRate;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;


}
