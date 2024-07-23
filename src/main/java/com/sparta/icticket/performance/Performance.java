package com.sparta.icticket.performance;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.performance.dto.PerformanceRequestDto;
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
    private GenreType genreType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgeGroup ageGroup;

    @Column(nullable = false)
    private Integer runTime;

    @Column(nullable = false)
    private LocalDateTime openAt;

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate endAt;

    private String imageUrl;

    private Long viewCount;

    public Performance(PerformanceRequestDto requestDto, Venue venue) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.venue = venue;
        this.genreType = requestDto.getGenreType();
        this.ageGroup = requestDto.getAgeGroup();
        this.runTime = requestDto.getRunTime();
        this.openAt = requestDto.getOpenAt();
        this.startAt = requestDto.getStartAt();
        this.endAt = requestDto.getEndAt();
        this.imageUrl = requestDto.getImageUrl();
        this.viewCount = 0L;
    }

    public void update(PerformanceRequestDto requestDto, Venue venue) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.venue = venue;
        this.genreType = requestDto.getGenreType();
        this.ageGroup = requestDto.getAgeGroup();
        this.runTime = requestDto.getRunTime();
        this.openAt = requestDto.getOpenAt();
        this.startAt = requestDto.getStartAt();
        this.endAt = requestDto.getEndAt();
        this.imageUrl = requestDto.getImageUrl();
    }
}
