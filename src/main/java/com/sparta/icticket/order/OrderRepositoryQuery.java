package com.sparta.icticket.order;

import java.util.List;

public interface OrderRepositoryQuery {

    List<String> findSeatNumberById(List<Long> seatIdList);

    Integer sumTotalPrice(List<Long> seatIdList);
}
