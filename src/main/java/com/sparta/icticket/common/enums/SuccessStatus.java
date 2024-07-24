package com.sparta.icticket.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum SuccessStatus {

    //라이브 정리 가겠습니다.
    // [USER]
    USER_SIGN_UP_SUCCESS(HttpStatus.OK, "회원가입에 성공하였습니다."),
    USER_DEACTIVATE_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공하였습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공하였습니다."),
    USER_GET_INFO_SUCCESS(HttpStatus.OK, "회원 정보 조회에 성공하였습니다."),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보 수정에 성공하였습니다."),

    // [PERFORMANCE],
    PERFORMANCE_CREATE_SUCCESS(HttpStatus.OK, "공연 등록에 성공하였습니다."),
    PERFORMANCE_DELETE_SUCCESS(HttpStatus.OK, "공연 삭제에 성공하였습니다."),
    PERFORMANCE_UPDATE_SUCCESS(HttpStatus.OK, "공연 수정에 성공하였습니다."),
    PERFORMANCE_GET_INFO_SUCCESS(HttpStatus.OK, "단일 공연 조회에 성공하였습니다."),
    PERFORMANCE_GET_GENRE_RANKING_SUCCESS(HttpStatus.OK, "장르별 랭킹 조회에 성공하였습니다."),
    PERFORMANCE_GET_TODAY_OPEN_SUCCESS(HttpStatus.OK, "오늘 오픈 공연 조회에 성공하였습니다."),
    PERFORMANCE_GET_RECOMMEND_SUCCESS(HttpStatus.OK, "추천 티켓 조회에 성공하였습니다."),
    PERFORMANCE_GET_DISCOUNT_SUCCESS(HttpStatus.OK, "할인 중인 공연 조회에 성공하였습니다."),
    PERFORMANCE_GET_WILL_BE_OPEN_SUCCESS(HttpStatus.OK, "오픈 예정 공연 조회에 성공하였습니다."),
    PERFORMANCE_GET_RANK_ALL_SUCCESS(HttpStatus.OK, "전체 랭킹 조회에 성공하였습니다."),

    //[LIKE]
    LIKE_ADD_SUCCESS(HttpStatus.OK, "관심 공연 등록에 성공하였습니다."),
    LIKE_UNLIKE_SUCCESS(HttpStatus.OK, "관심 공연 등록이 취소되었습니다."),
    LIKE_GET_COUNT_SUCCESS(HttpStatus.OK, "관심 개수 조회에 성공하였습니다."),
    LIKE_GET_ISLIKED_SUCCESS(HttpStatus.OK, "단일 공연 좋아요 여부 조회에 성공하였습니다."),

    //[COMMENT]
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "댓글 작성에 성공하였습니다."),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK, "댓글 삭제에 성공하였습니다."),
    COMMENT_GET_INFOS_SUCCESS(HttpStatus.OK, "단일 공연 댓글 조회에 성공하였습니다."),

    //[SESSION],
    SESSION_CREATE_SUCCESS(HttpStatus.OK, "세션 등록에 성공하였습니다."),
    SESSION_UPDATE_SUCCESS(HttpStatus.OK, "세션 수정에 성공하였습니다."),
    SESSION_DELETE_SUCCESS(HttpStatus.OK, "세션 삭제에 성공하였습니다."),
    SESSION_GET_INFOS_SUCCESS(HttpStatus.OK, "세션 조회에 성공하였습니다."),
    SESSION_GET_SEAT_INFOS_SUCCESS(HttpStatus.OK, "세션 잔여 좌석 조회에 성공하였습니다."),

    //[VENUE]
    VENUE_CREATE_SUCCESS(HttpStatus.OK, "공연장 등록에 성공하였습니다."),
    VENUE_UPDATE_SUCCESS(HttpStatus.OK, "공연장 정보 수정이 완료되었습니다."),
    VENUE_DELETE_SUCCESS(HttpStatus.OK, "공연장 삭제에 성공하였습니다."),

    //[ORDER]
    ORDER_CREATE_SUCCESS(HttpStatus.OK, "티켓 예매가 완료되었습니다."),
    ORDER_CANCEL_SUCCESS(HttpStatus.OK, "티켓 예매 취소가 완료되었습니다."),
    ORDER_GET_INFOS_SUCCESS(HttpStatus.OK, "예매내역 조회에 성공하였습니다."),

    //[SEAT]
    SEAT_CREATE_SUCCESS(HttpStatus.OK, "좌석 등록에 성공하였습니다."),
    SEAT_DELETE_SUCCESS(HttpStatus.OK, "좌석 삭제에 성공하였습니다."),
    SEAT_SELECT_SUCCESS(HttpStatus.OK, "좌석 선택에 성공하였습니다."),

    //[DISCOUNT]
    DISCOUNT_CREATE_SUCCESS(HttpStatus.OK, "할인 적용에 성공하였습니다."),
    DISCOUNT_UPDATE_SUCCESS(HttpStatus.OK, "할인 수정에 성공하였습니다."),
    DISCOUNT_DELETE_SUCCESS(HttpStatus.OK, "할인 삭제에 성공하였습니다."),

    //[BANNER]
    BANNER_CREATE_SUCCESS(HttpStatus.OK, "배너 등록에 성공하였습니다."),
    BANNER_DELETE_SUCCESS(HttpStatus.OK, "배너 삭제에 성공하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
