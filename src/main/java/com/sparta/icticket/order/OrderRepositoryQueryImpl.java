package com.sparta.icticket.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<String> findSeatNumberById(List<Long> seatIdList) {
        return queryFactory.select(seat.seatNumber)
                .from(seat)
                .where(seat.id.in(seatIdList))
                .fetch();
    }

    @Override
    public Integer sumTotalPrice(List<Long> seatIdList) {
        return queryFactory.select(seat.price.sum())
                .from(seat)
                .where(seat.id.in(seatIdList))
                .fetchOne();
    }
}
