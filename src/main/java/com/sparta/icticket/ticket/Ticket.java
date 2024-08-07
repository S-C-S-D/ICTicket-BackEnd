package com.sparta.icticket.ticket;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.order.Order;
import com.sparta.icticket.seat.Seat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tickets")
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

    // ticket 생성
    public Ticket(Order order, Seat seat, Integer price) {
        this.order = order;
        this.seat = seat;
        this.price = price;
    }

}
