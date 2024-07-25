package com.sparta.icticket.order;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.order.dto.OrderCreateRequestDto;
import com.sparta.icticket.order.dto.OrderCreateResponseDto;
import com.sparta.icticket.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 결제 완료 기능 구현
     * @param sessionId
     * @param requestDto
     * @param userDetails
     * @return
     */
    @PostMapping("/sessions/{sessionId}/orders")
    public ResponseEntity<ResponseDataDto<OrderCreateResponseDto>> createOrder(
            @PathVariable Long sessionId, @RequestBody @Valid OrderCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderCreateResponseDto responseDto = orderService.createOrder(sessionId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<OrderCreateResponseDto>(SuccessStatus.ORDER_CREATE_SUCCESS, responseDto));
    }
}
