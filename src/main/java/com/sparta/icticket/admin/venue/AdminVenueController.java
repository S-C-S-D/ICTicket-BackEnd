package com.sparta.icticket.admin.venue;

import com.sparta.icticket.admin.venue.dto.VenueRequestDto;
import com.sparta.icticket.admin.venue.dto.VenueResponseDto;
import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/venues")
public class AdminVenueController {

    private final AdminVenueService venueAdminService;

    @Autowired
    public AdminVenueController(AdminVenueService venueAdminService) {
        this.venueAdminService = venueAdminService;
    }

    //공연장 생성
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createVenue(
            @Valid @RequestBody VenueRequestDto venueRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        // 권한이 있는 경우, 공연장 생성 처리
        venueAdminService.createVenue(venueRequestDto, loginUser);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.VENUE_CREATE_SUCCESS));
    }

    //공연장 수정
    @PutMapping("/{venueId}")
    public ResponseEntity<ResponseMessageDto> updateVenue(
            @PathVariable Long venueId,
            @Valid @RequestBody VenueRequestDto venueRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        // 권한이 있는 경우, 공연장 수정
        venueAdminService.updateVenue(venueId, venueRequestDto, loginUser);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.VENUE_UPDATE_SUCCESS));
    }

    //공연장 삭제
    @DeleteMapping("/{venueId}")
    public ResponseEntity<ResponseMessageDto> deleteVenue(
            @PathVariable Long venueId,
            @Valid @RequestBody VenueRequestDto venueRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        // 권한이 있는 경우, 공연장 삭제
        venueAdminService.deleteVenue(venueId, venueRequestDto, loginUser);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.VENUE_DELETE_SUCCESS));
    }
}
