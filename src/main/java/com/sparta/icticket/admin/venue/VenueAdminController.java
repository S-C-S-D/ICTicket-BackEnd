package com.sparta.icticket.admin.venue;

import com.sparta.icticket.admin.venue.dto.VenueRequestDto;
import com.sparta.icticket.admin.venue.dto.VenueResponseDto;
import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/venues")
public class VenueAdminController {

    private final VenueAdminService venueAdminService;

    @Autowired
    public VenueAdminController(VenueAdminService venueAdminService) {
        this.venueAdminService = venueAdminService;
    }

    //공연장 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<VenueResponseDto>> createVenue(
            @Valid @RequestBody VenueRequestDto venueRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 사용자 권한 검사
        if (!isAdmin(userDetails)) {
            // 권한이 없는 경우, 에러 응답 반환
            return createErrorResponse(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

        // 권한이 있는 경우, 공연장 생성 처리
        VenueResponseDto venueResponseDto = venueAdminService.createVenue(venueRequestDto);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.VENUE_CREATE_SUCCESS, venueResponseDto));
    }

    // 권한 검사 메소드
    private boolean isAdmin(UserDetailsImpl userDetails) {
        return "ROLE_ADMIN".equals(userDetails.getUser().getUserRole().getAuthority());
    }

    // 에러 응답 생성 메소드
    private ResponseEntity<ResponseDataDto<VenueResponseDto>> createErrorResponse(ErrorType errorType) {
        SuccessStatus status = SuccessStatus.valueOf("ERROR_" + errorType.name());
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(status);
        // 에러 응답도 ResponseDataDto 형태로 감싸서 반환
        return ResponseEntity.status(errorType.getHttpStatus())
                .body(new ResponseDataDto<>(status, null));
    }
}
