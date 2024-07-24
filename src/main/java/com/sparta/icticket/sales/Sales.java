package com.sparta.icticket.sales;

import com.sparta.icticket.admin.controller.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.admin.controller.sales.dto.SalesUpdateRequestDto;
import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "sales")
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

    public Sales(Performance performance, SalesAddRequestDto requestDto) {
        this.performance = performance;
        this.discountRate = requestDto.getDiscountRate();
        this.startAt = requestDto.getStartAt();
        this.endAt = requestDto.getEndAt();
    }

    public void updateSales(SalesUpdateRequestDto requestDto) {
        this.discountRate = requestDto.getDiscountRate();
        this.startAt = requestDto.getStartAt();
        this.endAt = requestDto.getEndAt();
    }
}
