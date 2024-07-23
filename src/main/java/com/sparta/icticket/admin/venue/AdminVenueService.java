package com.sparta.icticket.admin.venue;

import com.sparta.icticket.admin.venue.dto.VenueRequestDto;
import com.sparta.icticket.admin.venue.dto.VenueResponseDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.user.User;
import com.sparta.icticket.venue.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminVenueService {

    private final AdminVenueRepository venueAdminRepository;

    @Autowired
    public AdminVenueService(AdminVenueRepository venueAdminRepository) {
        this.venueAdminRepository = venueAdminRepository;
    }

    //생성
    public void createVenue(VenueRequestDto venueRequestDto, User loginUser) {
        // 사용자 권한 검사
        if (!isAdmin(loginUser)) {
            // 권한이 없는 경우, 에러 응답 반환
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

        Venue venue = new Venue(venueRequestDto);
        venueAdminRepository.save(venue);
    }

    //수정
    @Transactional
    public void updateVenue(Long venueId, VenueRequestDto venueRequestDto, User loginUser) {
        // 사용자 권한 검사
        if (!isAdmin(loginUser)) {
            // 권한이 없는 경우, 에러 응답 반환
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
        Venue venue = venueAdminRepository.findById(venueId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_VENUE));
        venue.updateVenue(venueRequestDto);
    }

    //삭제
    public void deleteVenue(VenueRequestDto venueRequestDto, User loginUser) {
        Venue venue = new Venue(venueRequestDto);
        venueAdminRepository.save(venue);
    }


    // 권한 검사 메소드
    private boolean isAdmin(User user) {
        return UserRole.ADMIN.equals(user.getUserRole());
    }
}
