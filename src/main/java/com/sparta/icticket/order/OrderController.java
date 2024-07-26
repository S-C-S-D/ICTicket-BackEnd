package com.sparta.icticket.order;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.order.dto.OrderCreateRequestDto;
import com.sparta.icticket.order.dto.OrderCreateResponseDto;
import com.sparta.icticket.order.dto.OrderListResponseDto;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.ORDER_CREATE_SUCCESS, responseDto));
    }

    /**
     * 예매 내역 조회 기능
     * @param userId
     * @param userDetails
     * @return
     */
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<ResponseDataDto<List<OrderListResponseDto>>> getOrders(
            @PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<OrderListResponseDto> responseDto = orderService.getOrders(userId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.ORDER_GET_INFOS_SUCCESS, responseDto));
    }

    /**
     * 예매 취소
     */
    @PatchMapping("/users/orders/{orderId}")
    public ResponseEntity<ResponseMessageDto> deleteOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User loginUser = userDetails.getUser();
        orderService.deleteOrder(orderId, loginUser);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.ORDER_CANCEL_SUCCESS));
    }
}
