package com.sparta.icticket.order;

import com.sparta.icticket.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Integer countAllByUser(User user);
}
