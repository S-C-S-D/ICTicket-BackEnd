package com.sparta.icticket.ticket;

import com.sparta.icticket.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface TicketRepository extends JpaRepository<Ticket, Long>/*, TicketRepositoryQuery */{

    // 해당 order를 가진 ticket 객체 조회
    Optional<List<Ticket>> findByOrder(Order order);
}
