package com.sparta.icticket.venue;

import com.sparta.icticket.common.Timestamped;
import jakarta.persistence.*;

@Entity
public class Venue extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String venueName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Long totalSeatCount;
}
