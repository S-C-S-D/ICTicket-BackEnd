package com.sparta.icticket.order;

import com.sparta.icticket.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryQuery {

    // 해당 user를 가진 order 객체의 개수 조회
    Integer countAllByUser(User user);

    // 해당 user를 가진 order 객체를 careted_at을 기준으로 내림차순 조회
    List<Order> findAllByUserOrderByCreatedAtDesc(User loginUser);
}
