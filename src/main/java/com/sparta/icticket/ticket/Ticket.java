package com.sparta.icticket.ticket;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.order.Order;
import com.sparta.icticket.seat.Seat;
import jakarta.persistence.*;

@Entity
public class Ticket extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private Integer price;

}
