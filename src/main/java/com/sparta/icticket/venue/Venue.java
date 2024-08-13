package com.sparta.icticket.venue;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.venue.dto.VenueRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "venues")
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

    public Venue(VenueRequestDto venueRequestDto) {
        this.venueName = venueRequestDto.getVenueName();
        this.location = venueRequestDto.getLocation();
        this.totalSeatCount = venueRequestDto.getTotalSeatCount();
    }

    public Venue() {
    }

    public void updateVenue(VenueRequestDto venueRequestDto) {
        this.venueName = venueRequestDto.getVenueName();
        this.location = venueRequestDto.getLocation();
        this.totalSeatCount = venueRequestDto.getTotalSeatCount();
    }
}
