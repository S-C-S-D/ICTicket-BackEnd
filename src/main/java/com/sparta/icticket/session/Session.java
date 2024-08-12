package com.sparta.icticket.session;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.session.dto.CreateSessionRequestDto;
import com.sparta.icticket.session.dto.UpdateSessionRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "sessions")
public class Session extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(nullable = false)
    private LocalTime sessionTime;

    @Column(nullable = false)
    private String sessionName;

    public Session(Performance performance, CreateSessionRequestDto createSessionRequestDto) {
        this.performance = performance;
        this.sessionDate = createSessionRequestDto.getDate();
        this.sessionTime = createSessionRequestDto.getTime();
        this.sessionName = createSessionRequestDto.getName();
    }

    public Session() {
    }

    public void update(UpdateSessionRequestDto updateSessionRequestDto) {
        this.sessionDate = updateSessionRequestDto.getDate();
        this.sessionTime = updateSessionRequestDto.getTime();
        this.sessionName = updateSessionRequestDto.getName();
    }

    // 해당 공연기간에서 벗어난 날짜를 입력했을때 예외처리
    public void checkDate(LocalDate date) {
        if (date.isBefore(this.performance.getStartAt()) ||
                date.isAfter(this.performance.getEndAt())) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }
    }

    public void checkPerformance(Long performanceId) {
        if (!this.performance.getId().equals(performanceId)) {
            throw new CustomException(ErrorType.NOT_VALID_SESSION);
        }
    }


}
