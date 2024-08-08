package com.sparta.icticket.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    //[USER]
    ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    ALREADY_EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DEACTIVATE_USER(HttpStatus.BAD_REQUEST, "탈퇴한 회원입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.BAD_REQUEST, "권한이 없습니다."),

    //[PERFORMANCE]
    END_AT_PASSED_START_AT(HttpStatus.BAD_REQUEST, "공연 마감일이 시작일보다 빠릅니다."),
    NOT_FOUND_PERFORMANCE(HttpStatus.BAD_REQUEST, "존재하지 않는 공연입니다."),

    //[LIKE]
    ALREADY_LIKED_PERFORMANCE(HttpStatus.BAD_REQUEST, "이미 관심공연으로 등록한 공연입니다."),
    NOT_LIKED_PERFORMANCE(HttpStatus.BAD_REQUEST, "관심공연으로 등록된 공연이 아닙니다."),

    //[COMMENT]
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 공연의 댓글이 없습니다."),

    //[SESSION]
    NOT_FOUND_SESSION(HttpStatus.NOT_FOUND, "존재하지 않는 세션입니다."),
    NOT_AVAILABLE_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 날짜입니다."),
    ALREADY_EXISTS_SESSION_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 세션 이름입니다."),
    ALREADY_EXISTS_SESSION_TIME(HttpStatus.BAD_REQUEST, "이미 존재하는 세션 시간입니다."),
    ALREADY_EXISTS_SESSION(HttpStatus.BAD_REQUEST,"해당 날짜,이름,시간을 가진 세션이 존재합니다"),
    NOT_VALID_SESSION(HttpStatus.BAD_REQUEST,"해당 공연의 세션이 아닙니다."),
    WRONG_DATE_FORMAT(HttpStatus.BAD_REQUEST,"날짜 혹은 시간을 올바른 형식으로 입력해 주십시오."),
    NOT_FOUND_MODIFICATIONS(HttpStatus.BAD_REQUEST,"수정사항이 발견되지 않았습니다"),


    //[VENUE]
    NOT_FOUND_VENUE(HttpStatus.BAD_REQUEST, "존재하지 않는 공연장입니다."),

    //[SEAT]
    NOT_FOUND_SEAT(HttpStatus.NOT_FOUND, "존재하지 않는 좌석입니다."),
    ALREADY_RESERVED_SEAT(HttpStatus.BAD_REQUEST, "이미 예약이된 좌석입니다."),
    ALREADY_EXISTS_SEAT(HttpStatus.BAD_REQUEST, "이미 존재하는 좌석 번호입니다."),
    NOT_MATCHED_SESSION(HttpStatus.BAD_REQUEST, "해당 세션의 좌석이 아닙니다."),

    //[AUTHENTICATION]
    REQUIRES_LOGIN(HttpStatus.BAD_REQUEST, "로그인이 필요한 서비스입니다."),

    //[JWT]
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다. 다시 로그인 해주세요."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),

    // [DISCOUNT]
    NOT_FOUND_SALES(HttpStatus.NOT_FOUND, "존재하지 않는 할인 정보입니다."),
    ALREADY_EXISTS_SALES(HttpStatus.BAD_REQUEST, "이미 할인이 적용된 공연입니다."),

    // [ORDER]
    NOT_YOUR_ORDER(HttpStatus.BAD_REQUEST,"예매 취소 권한이 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND,"예매 내역이 존재하지 않습니다"),
    ALREADY_CANCELED_ORDER(HttpStatus.BAD_REQUEST, "이전에 예매 취소한 이력이 있습니다."),
    TIME_OUT(HttpStatus.LOCKED, "결제 시간이 초과 되었습니다."),


    // [BANNER]
    NOT_FOUND_BANNER(HttpStatus.NOT_FOUND, "존재하지 않는 배너입니다."),
    ALREADY_EXISTS_BANNER_POSITION(HttpStatus.BAD_REQUEST, "중복된 배너 위치입니다."),

    // [Order]
    CAN_NOT_LOAD_ORDER_HISTORY(HttpStatus.BAD_REQUEST, "예매 내역을 조회할 수 없습니다."),

    // [Ticket]
    NOT_FOUND_TICKET(HttpStatus.BAD_REQUEST,"티켓을 찾을 수 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
