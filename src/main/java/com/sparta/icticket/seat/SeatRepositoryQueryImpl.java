package com.sparta.icticket.seat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.icticket.common.enums.SeatStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.icticket.seat.QSeat.seat;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryQueryImpl implements SeatRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    /**
     * @Scheduled로 실행하는 쿼리문
     * @description seat_status가 PAYING이면서 reserved_at이 현재보다 10분 이상 지난 시간인 seat 객체의 seat_status를 NOT_RESERVED로 변경
     */
    @Override
    public void findSeatsBySeatStatus() {
        queryFactory
                .update(seat)
                .set(seat.seatStatus, SeatStatus.NOT_RESERVED)
                .set(seat.userId, (Long) null)
                .where(seat.seatStatus.eq(SeatStatus.PAYING).and(seat.seatSelectedAt.before(LocalDateTime.now().minusMinutes(10))))
                .execute();
    }

    /**
     * idList로 seat 객체 조회
     * @param seatIdList
     * @description 해당 seatIdList에 있는 id와 일치하는 id를 가진 seat 객체 조회
     */
    @Override
    public List<Seat> findSeatsByIdList(List<Long> seatIdList) {
        return queryFactory
                .selectFrom(seat)
                .where(seat.id.in(seatIdList).and(seat.seatStatus.eq(SeatStatus.NOT_RESERVED)))
                .fetch();
    }

}
