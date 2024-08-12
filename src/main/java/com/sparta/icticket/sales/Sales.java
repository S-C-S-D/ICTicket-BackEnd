package com.sparta.icticket.sales;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.sales.dto.SalesUpdateRequestDto;
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

    // 할인 객체 생성
    public Sales(Performance performance, SalesAddRequestDto requestDto) {
        this.performance = performance;
        this.discountRate = requestDto.getDiscountRate();
        this.startAt = requestDto.getStartAt();
        this.endAt = requestDto.getEndAt();
    }

    // 할인 적용 수정
    public void updateSales(SalesUpdateRequestDto requestDto) {
        this.discountRate = requestDto.getDiscountRate();
        this.startAt = requestDto.getStartAt();
        this.endAt = requestDto.getEndAt();
    }
}
