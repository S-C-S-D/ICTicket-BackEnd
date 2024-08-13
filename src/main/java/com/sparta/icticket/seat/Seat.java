package com.sparta.icticket.seat;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "seats")
public class Seat extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatGrade seatGrade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;

    private LocalDateTime seatSelectedAt;

    private Long userId;

    // 좌석 생성
    public Seat(Session session, SeatCreateRequestDto requestDto) {
        this.session = session;
        this.price = requestDto.getPrice();
        this.seatNumber = requestDto.getSeatNumber();
        this.seatGrade = requestDto.getSeatGrade();
        this.seatStatus = SeatStatus.NOT_RESERVED;
    }

    // seat_status를 PAYING으로 변경, reserved_at을 현재 시간으로 변경
    public void updateSeatStatusToPaying(User recentUser) {
        this.seatStatus = SeatStatus.PAYING;
        this.seatSelectedAt = LocalDateTime.now();
        this.userId = recentUser.getId();
    }

    // seat_status 변경
    public void updateSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public void checkUser(User loginUser) {
        if(!this.userId.equals(loginUser.getId())) {
            throw new CustomException(ErrorType.TIME_OUT);
        }
    }

    public void checkSession(Long sessionId) {
        if(!this.session.getId().equals(sessionId)) {
            throw new CustomException(ErrorType.NOT_MATCHED_SESSION);
        }
    }
}
