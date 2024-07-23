package com.sparta.icticket.admin.venue;

import com.sparta.icticket.admin.venue.dto.VenueRequestDto;
import com.sparta.icticket.admin.venue.dto.VenueResponseDto;
import com.sparta.icticket.venue.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueAdminService {

    private final VenueAdminRepository venueAdminRepository;

    @Autowired
    public VenueAdminService(VenueAdminRepository venueAdminRepository) {
        this.venueAdminRepository = venueAdminRepository;
    }

    public VenueResponseDto createVenue(VenueRequestDto venueRequestDto) {
        Venue venue = new Venue(venueRequestDto);
        venueAdminRepository.save(venue);
        return new VenueResponseDto(venue);
    }
}
