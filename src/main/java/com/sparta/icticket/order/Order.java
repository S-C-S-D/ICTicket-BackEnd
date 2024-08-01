package com.sparta.icticket.order;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.OrderStatus;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders", indexes = @Index(name = "Idx_orders", columnList = "create_at"))
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

    public Order(User user, Session session, String orderNumber, Integer ticketCount, Integer totalPrice) {
        this.user = user;
        this.session = session;
        this.orderNumber = orderNumber;
        this.ticketCount = ticketCount;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.SUCCESS;
    }

    public void updateOrderStatusToCancel() {
        this.orderStatus=OrderStatus.CANCEL;
    }
}
