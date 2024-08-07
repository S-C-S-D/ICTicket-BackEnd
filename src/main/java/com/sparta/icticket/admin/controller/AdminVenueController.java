package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminVenueService;
import com.sparta.icticket.venue.dto.VenueRequestDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.common.security.UserDetailsImpl;
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

    /**
     * 공연장 생성
     * @param venueRequestDto 공연장 등록 정보
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createVenue(
            @Valid @RequestBody VenueRequestDto venueRequestDto) {
        venueAdminService.createVenue(venueRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.VENUE_CREATE_SUCCESS));
    }

    /**
     * 공연장 수정
     * @param venueId 공연장 id
     * @param venueRequestDto 공연장 수정 정보
     * @return
     */
    @PatchMapping("/{venueId}")
    public ResponseEntity<ResponseMessageDto> updateVenue(
            @PathVariable Long venueId,
            @Valid @RequestBody VenueRequestDto venueRequestDto) {
        venueAdminService.updateVenue(venueId, venueRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.VENUE_UPDATE_SUCCESS));
    }

    /**
     * 공연장 삭제
     * @param venueId 공연장 id
     * @return
     */
    @DeleteMapping("/{venueId}")
    public ResponseEntity<ResponseMessageDto> deleteVenue(
            @PathVariable Long venueId) {
        venueAdminService.deleteVenue(venueId);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.VENUE_DELETE_SUCCESS));
    }
}
