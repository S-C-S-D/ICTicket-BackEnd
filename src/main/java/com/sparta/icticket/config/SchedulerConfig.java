package com.sparta.icticket.config;

import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.seat.SeatRepositoryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final SeatRepository seatRepository;

    @Transactional
//    @Scheduled(cron = "0 0/5 * * * ?") 필요 시 해제
    public void scheduled() {
        seatRepository.findSeatsBySeatStatus();
    }
}
