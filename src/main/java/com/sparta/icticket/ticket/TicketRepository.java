package com.sparta.icticket.ticket;

import com.sparta.icticket.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>, TicketRepositoryQuery {

    List<Ticket> findByOrder(Order order);
}
