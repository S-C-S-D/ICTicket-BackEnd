package com.sparta.icticket.seat;

import com.sparta.icticket.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.session.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@AllArgsConstructor
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

    private LocalDateTime reservedAt;

    public Seat(Session session, SeatCreateRequestDto requestDto) {
        this.session = session;
        this.price = requestDto.getPrice();
        this.seatNumber = requestDto.getSeatNumber();
        this.seatGrade = requestDto.getSeatGrade();
        this.seatStatus = SeatStatus.NOT_RESERVED;
    }

    public void updateSeatStatusToPaying() {
        this.seatStatus = SeatStatus.PAYING;
        this.reservedAt = LocalDateTime.now();
    }

    public void updateSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
}
