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
public class SeatRepositoryQueryImpl implements SeatRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public SeatRepositoryQueryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void findSeatsBySeatStatus() {
        queryFactory
                .update(seat)
                .set(seat.seatStatus, SeatStatus.NOT_RESERVED)
                .where(seat.seatStatus.eq(SeatStatus.PAYING).and(seat.reservedAt.before(LocalDateTime.now().minusMinutes(10))))
                .execute();
    }

    @Override
    public List<Seat> findSeatsByIdList(List<Long> seatIdList) {
        return queryFactory
                .selectFrom(seat)
                .where(seat.id.in(seatIdList).and(seat.seatStatus.eq(SeatStatus.NOT_RESERVED)))
                .fetch();
    }
}
