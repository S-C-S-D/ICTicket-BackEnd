package com.sparta.icticket.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    //[USER]
    ALREADY_EXISTS_EMAIL(HttpStatus.LOCKED, "이미 존재하는 이메일입니다."),
    ALREADY_EXISTS_NICKNAME(HttpStatus.LOCKED, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_USER(HttpStatus.LOCKED, "존재하지 않는 회원입니다."),
    NOT_FOUND_EMAIL(HttpStatus.LOCKED, "존재하지 않는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.LOCKED, "비밀번호가 일치하지 않습니다."),
    DEACTIVATE_USER(HttpStatus.LOCKED, "탈퇴한 회원입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.LOCKED, "권한이 없습니다."),

    //[PERFORMANCE]
    END_AT_PASSED_START_AT(HttpStatus.LOCKED, "공연 마감일이 시작일보다 빠릅니다."),
    WRONG_DATE_FORMAT(HttpStatus.LOCKED, "잘못된 날짜 입니다."),
    NOT_FOUND_PERFORMANCE(HttpStatus.LOCKED, "존재하지 않는 공연입니다."),
    NOT_FOUND_TODAY_OPEN_PERFORMANCES(HttpStatus.LOCKED, "오픈 예정인 공연이 없습니다."),
    NOT_FOUND_GENRE(HttpStatus.LOCKED, "존재하지 않는 장르입니다."),

    //[LIKE]
    ALREADY_LIKED_PERFORMANCE(HttpStatus.LOCKED, "이미 관심공연으로 등록한 공연입니다."),
    NOT_LIKED_PERFORMANCE(HttpStatus.LOCKED, "관심공연으로 등록된 공연이 아닙니다."),

    //[COMMENT]
    NOT_FOUND_COMMENT(HttpStatus.LOCKED, "존재하지 않는 댓글입니다."),

    //[SESSION]
    NOT_FOUND_SESSION(HttpStatus.LOCKED, "존재하지 않는 세션입니다."),
    NOT_AVAILABLE_DATE(HttpStatus.LOCKED, "유효하지 않은 날짜입니다."),
    ALREADY_EXISTS_SESSION_NAME(HttpStatus.LOCKED, "이미 존재하는 세션 이름입니다."),
    ALREADY_EXISTS_SESSION_TIME(HttpStatus.LOCKED,"이미 존재하는 세션 시간입니다."),
    NOT_VALID_SESSION(HttpStatus.LOCKED,"해당 공연의 세션이 아닙니다."),

    //[VENUE]
    NOT_FOUND_VENUE(HttpStatus.LOCKED, "존재하지 않는 공연장입니다."),

    //[SEAT]
    NOT_FOUND_SEAT(HttpStatus.LOCKED, "존재하지 않는 좌석입니다."),
    ALREADY_RESERVED_SEAT(HttpStatus.LOCKED, "이미 예약이된 좌석입니다."),

    //[AUTHENTICATION]
    REQUIRES_LOGIN(HttpStatus.LOCKED, "로그인이 필요한 서비스입니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),

    //[JWT]
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다. 다시 로그인 해주세요."),
    LOGGED_OUT_TOKEN(HttpStatus.FORBIDDEN, "이미 로그아웃된 토큰입니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    EXPIRED_JWT(HttpStatus.FORBIDDEN, "만료된 JWT 입니다."),

    // [DISCOUNT]
    NOT_FOUND_SALES(HttpStatus.LOCKED, "존재하지 않는 할인 정보입니다."),
    ALREADY_EXISTS_SALES(HttpStatus.LOCKED, "이미 할인이 적용된 공연입니다."),

    // [BANNER]
    NOT_FOUND_BANNER(HttpStatus.NOT_FOUND, "존재하지 않는 배너입니다."),
    ALREADY_EXISTS_BANNER_POSITION(HttpStatus.LOCKED, "중복된 배너 위치입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
