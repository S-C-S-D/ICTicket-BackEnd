package com.sparta.icticket.order;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.OrderStatus;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.user.User;
import jakarta.persistence.*;

@Entity
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private Integer ticketCount;

    @Column(nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

}
