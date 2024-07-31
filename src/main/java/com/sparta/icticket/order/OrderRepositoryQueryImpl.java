package com.sparta.icticket.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.session.Session;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.icticket.seat.QSeat.seat;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryQueryImpl implements OrderRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Seat> findSeatById(List<Long> seatIdList, Session session) {
         return queryFactory
                 .selectFrom(seat)
                 .where(seat.id.in(seatIdList).and(seat.seatStatus.eq(SeatStatus.PAYING))
                         .and(seat.session.eq(session)))
                 .fetch();
    }
}
