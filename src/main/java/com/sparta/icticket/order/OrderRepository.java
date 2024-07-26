package com.sparta.icticket.order;

import com.sparta.icticket.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryQuery {
    Integer countAllByUser(User user);

    List<Order> findAllByUserOrderByCreatedAtDesc(User loginUser);
}
