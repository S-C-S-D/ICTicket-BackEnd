package com.sparta.icticket.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.icticket.seat.Seat;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.icticket.seat.QSeat.seat;

@Repository
public class OrderRepositoryQueryImpl implements OrderRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryQueryImpl(EntityManager em, JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Seat> findSeatById(List<Long> seatIdList) {
         return queryFactory
                 .selectFrom(seat)
                 .where(seat.id.in(seatIdList))
                 .fetch();
    }
}
