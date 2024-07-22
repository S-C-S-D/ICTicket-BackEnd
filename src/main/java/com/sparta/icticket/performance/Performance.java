package com.sparta.icticket.performance;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.Genre;
import com.sparta.icticket.venue.Venue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "performances")
public class Performance extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id",nullable = false)
    private Venue venue;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgeGroup ageGroup;

    @Column(nullable = false)
    private String runTime;

    @Column(nullable = false)
    private LocalDateTime openAt;

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate endAt;

    private String imageUrl;

    private Long viewCount;

}
