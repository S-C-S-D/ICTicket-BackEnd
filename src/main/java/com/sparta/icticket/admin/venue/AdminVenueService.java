package com.sparta.icticket.admin.venue;

import com.sparta.icticket.admin.venue.dto.VenueRequestDto;
import com.sparta.icticket.admin.venue.dto.VenueResponseDto;
import com.sparta.icticket.venue.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminVenueService {

    private final AdminVenueRepository venueAdminRepository;

    @Autowired
    public AdminVenueService(AdminVenueRepository venueAdminRepository) {
        this.venueAdminRepository = venueAdminRepository;
    }

    //생성
    public VenueResponseDto createVenue(VenueRequestDto venueRequestDto) {
        Venue venue = new Venue(venueRequestDto);
        venueAdminRepository.save(venue);
        return new VenueResponseDto(venue);
    }

    //수정
    public VenueResponseDto updateVenue(VenueRequestDto venueRequestDto) {
        Venue venue = new Venue(venueRequestDto);
        venueAdminRepository.save(venue);
        return new VenueResponseDto(venue);
    }

    //삭제
    public VenueResponseDto deleteVenue(VenueRequestDto venueRequestDto) {
        Venue venue = new Venue(venueRequestDto);
        venueAdminRepository.save(venue);
        return new VenueResponseDto(venue);
    }
}
