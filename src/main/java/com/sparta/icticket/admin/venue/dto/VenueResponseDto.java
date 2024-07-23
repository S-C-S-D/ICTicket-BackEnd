package com.sparta.icticket.admin.venue.dto;

import com.sparta.icticket.venue.Venue;
import lombok.Getter;

@Getter
public class VenueResponseDto {

    private Long id;
    private String venueName;
    private String location;
    private Long totalSeatCount;

    public VenueResponseDto(Venue venue) {
        this.id = venue.getId();
        this.venueName = venue.getVenueName();
        this.location = venue.getLocation();
        this.totalSeatCount = venue.getTotalSeatCount();
    }
}
