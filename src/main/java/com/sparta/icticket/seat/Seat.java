package com.sparta.icticket.seat;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.session.Session;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
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

    @Column(nullable = false)
    private boolean isReserved;

    private LocalDateTime reservedAt;

}
