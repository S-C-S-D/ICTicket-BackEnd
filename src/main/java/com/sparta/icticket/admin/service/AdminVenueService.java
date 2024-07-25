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

    //생성
    public void createVenue(VenueRequestDto venueRequestDto, User loginUser) {
        // 사용자 권한 검사
        if (!isAdmin(loginUser)) {
            // 권한이 없는 경우, 에러 응답 반환
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

        Venue venue = new Venue(venueRequestDto);
        venueRepository.save(venue);
    }

    //수정
    @Transactional
    public void updateVenue(Long venueId, VenueRequestDto venueRequestDto, User loginUser) {
        // 사용자 권한 검사
        if (!isAdmin(loginUser)) {
            // 권한이 없는 경우, 에러 응답 반환
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_VENUE));
        venue.updateVenue(venueRequestDto);
    }

    //삭제
    public void deleteVenue(Long venueId, User loginUser) {
        // 사용자 권한 검사
        if (!isAdmin(loginUser)) {
            // 권한이 없는 경우, 에러 응답 반환
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_VENUE));

        venueRepository.delete(venue);
    }


    // 권한 검사 메소드
    private boolean isAdmin(User user) {
        return UserRole.ADMIN.equals(user.getUserRole());
    }
}
