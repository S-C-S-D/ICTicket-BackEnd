package com.sparta.icticket.like;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.user.User;
import jakarta.persistence.*;

@Entity
public class Like extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

}
