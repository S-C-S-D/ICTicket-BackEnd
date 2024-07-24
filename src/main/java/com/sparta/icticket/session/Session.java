package com.sparta.icticket.session;

import com.sparta.icticket.admin.session.dto.CreateSessionRequestDto;
import com.sparta.icticket.admin.session.dto.UpdateSessionRequestDto;
import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
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
        this.sessionName= createSessionRequestDto.getName();
    }


    public void update(UpdateSessionRequestDto updateSessionRequestDto) {
        this.sessionDate = updateSessionRequestDto.getDate();
        this.sessionTime = updateSessionRequestDto.getTime();
        this.sessionName= updateSessionRequestDto.getName();
    }
}
