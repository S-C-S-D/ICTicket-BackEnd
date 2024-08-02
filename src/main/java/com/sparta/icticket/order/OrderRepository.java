package com.sparta.icticket.order;

import com.sparta.icticket.seat.SeatRepositoryQuery;
import com.sparta.icticket.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, SeatRepositoryQuery {

    Integer countAllByUser(User user);

    @Query("select o from Order o join fetch o.session join fetch o.session.performance " +
            "join fetch o.session.performance.venue where o.user = :loginUser")
    List<Order> findAllByUserOrderByCreatedAtDesc(@Param("loginUser") User loginUser);
}
