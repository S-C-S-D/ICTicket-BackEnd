package com.sparta.icticket.session;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;

@Entity
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
    private Time sessionTime;

    @Column(nullable = false)
    private String sessionName;

}
