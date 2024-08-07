package com.sparta.icticket.admin.service;

import com.sparta.icticket.venue.dto.VenueRequestDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.user.User;
import com.sparta.icticket.venue.Venue;
import com.sparta.icticket.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminVenueService {

    private final VenueRepository venueRepository;

    /**
     * 공연장 생성
     * @param venueRequestDto
     */
    public void createVenue(VenueRequestDto venueRequestDto) {
        Venue venue = new Venue(venueRequestDto);
        venueRepository.save(venue);
    }

    /**
     * 공연장 수정
     * @param venueId
     * @param venueRequestDto
     */
    @Transactional
    public void updateVenue(Long venueId, VenueRequestDto venueRequestDto) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_VENUE));
        venue.updateVenue(venueRequestDto);
    }

    /**
     * 공연장 삭제
     * @param venueId
     */
    public void deleteVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_VENUE));
        venueRepository.delete(venue);
    }

}
