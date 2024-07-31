package com.sparta.icticket;

import com.sparta.icticket.banner.Banner;
import com.sparta.icticket.banner.BannerRepository;
import com.sparta.icticket.common.enums.*;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.user.User;
import com.sparta.icticket.user.UserRepository;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import com.sparta.icticket.venue.Venue;
import com.sparta.icticket.venue.VenueRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateDummyData {

    @Autowired
    PerformanceRepository performanceRepository;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    BannerRepository bannerRepository;
    @Autowired
    UserRepository userRepository;

    // 공연장 - 공연 - 세일 - 세션 - 시트
    // 유저 - 배너
    @Test
    @Order(1)
    @Transactional()
    @Rollback(value = false)
    void createVenueDummyData() {
        Venue venue = new Venue(1L, "서울숲씨어터", "서울특별시 성동구 서울숲2길 32-14", 80L);
        Venue venue1 = new Venue(2L, "수원월드컵경기장 주경기장", "경기 수원시 팔달구 월드컵로 310", 80L);

        venueRepository.save(venue);
        venueRepository.save(venue1);
    }

    @Test
    @Order(2)
    @Transactional()
    @Rollback(value = false)
    void createPerformanceDummyData() {
        String[] urls = {
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010432_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009872_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010203_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010160_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008528_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008445_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006714_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010300_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009876_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010346_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240702113349_23017234.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008308_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006383_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240719115251_24009968.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240712111345_23018044.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2406%2F240621033249_24008199.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2406%2F240617042447_24008151.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009903_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240712115758_24009691.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240719102350_24010200.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2405%2F240513101611_24006741.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007345_p.gif&w=384&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008243_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008248_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2405%2F240517102408_24006709.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009638_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24000638_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006256_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2406%2F240628102102_L0000091.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010088_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009645_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008121_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008784_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008642_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24000981_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24000982_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2406%2F240624013859_24009018.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240719110451_24010137.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2406%2F240625114850_24007505.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009059_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009039_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2405%2F240508041220_24006515.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006875_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008917_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24004835_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2403%2F240327110156_24004604.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2406%2F240625010303_24009003.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2406%2F240603103434_24007703.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007996_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24005493_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2406%2F240613013431_24003697.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006591_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007843_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240724015307_24009268.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008475_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008740_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2406%2F240613025857_24006159.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007912_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006065_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006164_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2406%2F240603102545_24007639.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006932_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2405%2F240514034254_24006465.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2406%2F240605025809_24007910.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24005912_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2405%2F240510010726_24006677.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007874_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008034_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FMusc%2F2404%2F240418025952_22014959.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F23%2F23018708_p.gif&amp;w=3840&amp;q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240712114746_24009415.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007256_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2FL0%2FL0000094_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24003322_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24003321_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240712115445_24009660.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008745_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007898_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240717011453_24008323.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009857_p.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009195_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24005568_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008696_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009895_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009383_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009510_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006288_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24005265_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009944_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009941_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240718014640_24009837.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240719103840_24010258.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240717022929_24005679.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240722044607_24007401.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FGMain%2FClas%2F2407%2F240722045813_24007887.gif&w=3840&q=7",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006797_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2FL0%2FL0000092_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010520_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006928_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006307_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007402_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010573_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010610_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009578_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24003561_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007319_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007623_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010289_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006288_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24005265_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24005706_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010091_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F23%2F23008491_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009913_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010378_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F21%2F21013096_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F22%2F22001006_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F21%2F21013249_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24004660_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010538_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010432_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009872_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010203_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010160_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008528_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24008445_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24006714_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010300_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009876_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010346_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F22%2F22001159_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24002802_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F23%2F23005704_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24000171_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F22%2F22014277_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007536_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24007909_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24009442_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24001202_p.gif&w=3840&q=75",
                "https://tickets.interpark.com/contents/_next/image?url=https%3A%2F%2Fticketimage.interpark.com%2FPlay%2Fimage%2Flarge%2F24%2F24010358_p.gif&w=3840&q=75"
        };

        String[] titles = {
                "2024 수아레콘서트 - 김태우와 하고 싶은 말 (08.28)",
                "손병호＆최지연과 함께하는 8월 여민락콘서트 -지금 뛰다NOWJUMP -세종시",
                "2024 아트 포레스트 페스티벌 ART FOR;REST FESTIVAL",
                "2024 장윤정 라이브 콘서트 - 대구",
                "2024 TREASURE RELAY TOUR ［REBOOT］ FINAL IN SEOUL",
                "2024 CassCool Festival",
                "올리비아 로드리고 첫 내한공연",
                "2024 유채훈 단독 콘서트 〈Sfumato〉",
                "NELL CLUB CONCERT 2024 ‘Our Eutopia’",
                "대구 - 2024 THE HYPER DAY (더하이퍼데이)",
                "문화릴레이티켓",
                "2024 오페라 투란도트 아레나 디 베로나 오리지널",
                "2024 평창대관령음악제",
                "［SIDance2024］서울 세계 무용 축제",
                "국립심포니오케스트라",
                "제21회 대구국제오페라축제",
                "르노 카퓌송 with 스위스 로잔챔버오케스트라 내한 공연",
                "정명훈 ＆ 라 페니체 오케스트라",
                "콘체르토 마라톤 프로젝트 - 선우예권의 라흐마니노프 피아노 협주곡",
                "차이코프스키를 위하여",
                "어쩌면 해피엔딩",
                "베르사유의 장미",
                "시데레우스",
                "유진과 유진",
                "빨래",
                "선천적 얼간이들",
                "더 맨 얼라이브",
                "에밀",
                "흔해빠진일",
                "룰렛",
                "서커스 발레 〈백조의 호수〉",
                "클라라 주미 강 바이올린 리사이틀",
                "마리아 조앙 피레스 피아노 리사이틀",
                "트리오 인: 드보르자크",
                "국립오페라단 〈탄호이저〉",
                "국립오페라단 〈서부의 아가씨〉",
                "콘서트오페라 〈카르멘〉",
                "스미노 하야토 피아노 리사이틀",
                "니콜라이 루간스키 피아노 리사이틀",
                "2024 발트앙상블 ＆ 사무엘 윤",
                "홍련",
                "클럽 드바이",
                "피터팬 온 아이스",
                "비밀의 화원",
                "베어 더 뮤지컬",
                "6시 퇴근",
                "와일드 와일드 : 애프터파티",
                "2024 최현우 아판타시아",
                "디어 에반 핸슨 - 부산",
                "연남동 빙굴빙굴 빨래방",
                "고잉홈프로젝트",
                "알렉상드르 캉토로프 피아노 리사이틀",
                "카운터테너 이동규 리사이틀 〈Dream Quilter : 꿈을 누비는 자〉",
                "임동민 피아니스트 리사이틀",
                "고전과 낭만사이 〈대니 구 ＆ 일리야 라쉬코프스키〉",
                "고전과 낭만사이 〈베토벤 ＆ 차이코프스키 ＆ 라흐마니노프〉",
                "박재홍 피아노 리사이틀",
                "장 하오첸 피아노 리사이틀",
                "2024 오르간 시리즈 Ⅱ. 이민준 오르간 리사이틀",
                "인 하우스 아티스트 한재민 〈트리오 리사이틀〉",
                "조선 이야기꾼 전기수",
                "살리에르",
                "메노포즈",
                "아가사",
                "미오 프라텔로",
                "이블데드",
                "썸데이",
                "연남장 캬바레",
                "페인터즈",
                "김종욱 찾기",
                "레이 첸 바이올리니스트 내한 리사이틀",
                "양인모 ＆ 베를린 바로크 솔리스텐",
                "크레디아 클래식 클럽 2024 그대가 꽃이라면",
                "2024 힉엣눙크! - 세종솔로이스츠의 Pure Lyricism",
                "2024 힉엣눙크! - 세종솔로이스츠 ＆ Four Concertmasters",
                "한독문화교류 ADeKo Gala Concert",
                "로잔 체임버 오케스트라 with 르노 카퓌송, 한재민＆이진상",
                "2024 크레디아 파크콘서트 - 유키 구라모토/대니구/포르테나",
                "2024 크레디아 파크콘서트 - 디즈니 인 콘서트",
                "2024 디즈니 인 콘서트 - Once Upon a Time",
                "2024 전주얼티밋뮤직페스티벌 사전예매",
                "손태진 ＆ 에녹 콘서트 in 수원",
                "〈미스트롯3〉 전국투어 콘서트 - 천안",
                "2024 심수봉 전국투어콘서트：꽃길 - 성남",
                "2024 Jang Wooyoung Fan meeting 〈Be Young〉",
                "소란 여름 콘서트 ‘Squeeze!’",
                "코난 그레이 내한공연",
                "HAVE A NICE TRIP 2024",
                "［2024 춘천공연예술제］음악－반도,그레이바이실버",
                "［2024 춘천공연예술제］음악－빅바플,일렉트릭플라워즈,수에뇨",
                "엘소드 오케스트라 : 메모리 오브 엘리오스_앙코르",
                "2024 동방프로젝트 오케스트라 콘서트 ∼ Invitation from Gensokyo",
                "더 시네마 ＆ 클래식 _ 영화음악 OST 콘서트",
                "스즈메의 문단속＆너의 이름은.＆날씨의 아이 공식 필름콘서트",
                "너의 이름은. 공식 필름콘서트",
                "연극 〈맥베스〉",
                "연극 〈엔젤스 인 아메리카 - 파트원:밀레니엄이 다가온다〉",
                "M 연극 시리즈 2. 연극 〈보도지침〉",
                "대학로 1위 연극 〈쉬어매드니스〉",
                "연극 〈꽃, 별이 지나〉 : 공연배달서비스 간다 20주년 퍼레이드",
                "싸이흠뻑쇼 SUMMERSWAG2024 - 수원",
                "마이클 리 단독 콘서트 ［Favorite Songs］ - 안양",
                "신인류 단독 콘서트 〈푸른 열대〉－부산",
                "［BABYMONSTER PRESENTS : SEE YOU THERE］ IN SEOUL",
                "노엘 갤러거 하이 플라잉 버즈",
                "싸이흠뻑쇼 SUMMERSWAG2024 - 속초",
                "두아 리파 내한공연",
                "AAA TOUR BY HYUKOH ＆ SUNSET ROLLERCOASTER",
                "코난 그레이 내한공연",
                "HAVE A NICE TRIP 2024",
                "연극 〈햄릿〉",
                "피오와 함께 돌아온 연극 〈너츠〉",
                "16년 블랙코미디 1위 연극 〈죽여주는 이야기〉",
                "연극 〈사운드 인사이드〉",
                "라이어 1탄 - 화성",
                "10년 연속 1위 연극〈옥탑방고양이〉- 틴틴홀",
                "4D공포연극 〈스위치〉",
                "연극 〈2호선세입자〉 : 지하철 생존 코미디",
                "뮤직드라마 〈불편한 편의점〉 - 부산",
                "연극 〈이방인〉",
                "2024 수아레콘서트 - 김태우와 하고 싶은 말 (08.28)",
                "손병호＆최지연과 함께하는 8월 여민락콘서트 -지금 뛰다NOWJUMP -세종시",
                "2024 아트 포레스트 페스티벌 ART FOR;REST FESTIVAL",
                "2024 장윤정 라이브 콘서트 - 대구",
                "2024 TREASURE RELAY TOUR ［REBOOT］ FINAL IN SEOUL",
                "2024 CassCool Festival",
                "올리비아 로드리고 첫 내한공연",
                "2024 유채훈 단독 콘서트 〈Sfumato〉",
                "NELL CLUB CONCERT 2024 ‘Our Eutopia’",
                "대구 - 2024 THE HYPER DAY (더하이퍼데이)",
                "연극〈늘근도둑이야기〉",
                "뮤직드라마 〈불편한 편의점〉 - 올웨이즈씨어터",
                "행오버",
                "［서울 대학로］연극 라면",
                "연극 한뼘사이",
                "〈바스커빌 : 셜록홈즈 미스터리〉 Chapter2",
                "연극 〈이기동 체육관〉 - 앵콜",
                "경기도극단 레퍼토리시즌 〈매달린 집〉 - 수원",
                "대학로 청소년연극 〈사춘기메들리〉",
                "연극 〈‘고도를 기다리며’를 기다리며〉"
        };
        String[] descriptions = {
                "김태우가 전하는 감동의 메시지와 함께하는 수아레콘서트. 진솔한 이야기와 음악이 함께합니다.",
                "손병호와 최지연이 함께하는 여민락 콘서트, 세종시에서 펼쳐지는 특별한 무대입니다.",
                "자연 속에서 즐기는 예술 축제, 아트 포레스트 페스티벌에서 다양한 공연과 전시를 만나보세요.",
                "트로트 여왕 장윤정의 라이브 콘서트, 대구에서 열리는 그녀의 명곡들을 라이브로 즐겨보세요.",
                "아이돌 그룹 TREASURE의 리레이 투어 마지막 공연, 서울에서 펼쳐지는 대단원의 막을 함께하세요.",
                "시원한 맥주와 함께 즐기는 CassCool Festival, 다양한 음악 공연과 이벤트가 기다리고 있습니다.",
                "세계적인 팝스타 올리비아 로드리고의 첫 내한공연, 그녀의 감성적인 음악을 라이브로 만나보세요.",
                "유채훈의 단독 콘서트 'Sfumato', 그의 깊은 음악 세계를 느낄 수 있는 무대입니다.",
                "밴드 넬의 클럽 콘서트, 'Our Eutopia'에서 넬의 독특한 음악과 분위기를 경험해보세요.",
                "대구에서 열리는 'THE HYPER DAY' 페스티벌, 다양한 음악 장르와 함께하는 하루입니다.",
                "김태우가 전하는 감동의 메시지와 함께하는 수아레콘서트. 진솔한 이야기와 음악이 함께합니다.",
                "손병호와 최지연이 함께하는 여민락 콘서트, 세종시에서 펼쳐지는 특별한 무대입니다.",
                "자연 속에서 즐기는 예술 축제, 아트 포레스트 페스티벌에서 다양한 공연과 전시를 만나보세요.",
                "트로트 여왕 장윤정의 라이브 콘서트, 대구에서 열리는 그녀의 명곡들을 라이브로 즐겨보세요.",
                "아이돌 그룹 TREASURE의 리레이 투어 마지막 공연, 서울에서 펼쳐지는 대단원의 막을 함께하세요.",
                "시원한 맥주와 함께 즐기는 CassCool Festival, 다양한 음악 공연과 이벤트가 기다리고 있습니다.",
                "세계적인 팝스타 올리비아 로드리고의 첫 내한공연, 그녀의 감성적인 음악을 라이브로 만나보세요.",
                "유채훈의 단독 콘서트 'Sfumato', 그의 깊은 음악 세계를 느낄 수 있는 무대입니다.",
                "밴드 넬의 클럽 콘서트, 'Our Eutopia'에서 넬의 독특한 음악과 분위기를 경험해보세요.",
                "대구에서 열리는 'THE HYPER DAY' 페스티벌, 다양한 음악 장르와 함께하는 하루입니다.",
                "어쩌면, 이 사랑이 해피엔딩일지도 모른다. 미래 도시 서울에서, 낡은 로봇 헬퍼 2호 올리버와 헬퍼 3호 클레어는 주인에게 버려진 뒤 홀로 살아가고 있다. 이들은 감정 없는 기계로 태어났지만, 서로를 만나면서 점차 인간적인 감정을 느끼기 시작한다. 기계와 인간의 경계를 넘나들며 진정한 사랑의 의미를 찾아가는 두 로봇의 이야기.",
                "이 책은 철저한 연구를 통해 기존의 전기들에 실려 있던 위조된 편지나 후대에 센세이션을 노려 만들어진 조잡한 에피소드 등을 배제하고,[2] 정치적 희생물로서의 마리 앙투아네트라는 인간을 묘사하는데 성공했다는 평가를 받는 작품이다. 덕분에 역사 속 인물의 성격과 운명을 묘사하는 필력은 그야말로 독자들을 매료시킬 만큼 압도적이다. 특히 마리 앙투아네트라는 인물 자체가 세간의 오해와 편견을 상당 부분 뒤집어쓰고 있었기 때문에 인물의 재발견이라는 점에서 이 작품의 평가는 좋은 편이다. 게다가 슈테판 츠바이크의 문장들을 보면 흡입력이 굉장한데 특히 이 작품에선 그 필력 면에서 정점을 찍는다. 이 때문에 1930년대 초반 발표 이후 100여년 가까이 지난 이후에도 이 책이 계속 발간된다.",
                "별을 향한 꿈, 그 누구도 막을 수 없다. 갈릴레오 갈릴레이는 천문학의 새로운 장을 열기 위해 끊임없이 연구하고, 결국 망원경을 통해 새로운 세계를 발견한다. 그의 발견은 교회와 충돌하며 큰 파장을 일으키지만, 그는 진실을 밝히기 위해 끝까지 싸운다. 별을 향한 집념과 진리의 탐구를 그린 감동적 이야기.",
                "어린 시절의 상처가 두 소녀를 연결한다. 유진과 유진, 두 소녀는 같은 이름을 가졌지만 서로 다른 환경에서 자란다. 하지만 둘은 어린 시절의 아픔을 공유하며 서로를 이해하고 치유해 간다. 상처받은 영혼들이 서로를 통해 성장하고 위로받는 이야기.",
                "삶의 무게를 함께 나누는 이웃들. 서울 변두리의 한 작은 동네, 세입자들은 각자의 사연을 가지고 살아간다. 고된 삶 속에서도 서로를 위로하며 희망을 찾는 사람들. 그들의 삶과 사랑, 그리고 작은 행복을 그린 이야기.",
                "세상은 그들을 얼간이라고 부르지만, 그들은 누구보다 순수하다. 선천적으로 조금 특별한 두 친구, 그들은 일반적인 사람들과는 다른 시선으로 세상을 바라본다. 그들의 순수한 마음과 독특한 시각은 주위 사람들에게 큰 감동을 준다.",
                "죽음에서 돌아온 남자, 그의 비밀은? 어느 날, 죽은 줄로만 알았던 한 남자가 돌아온다. 그의 귀환은 많은 사람들에게 충격을 주고, 그의 비밀을 밝히기 위해 주변 사람들은 그의 과거를 파헤치기 시작한다. 죽음과 삶의 경계를 넘나드는 미스터리.",
                "모험을 통해 진정한 자신을 발견하다. 에밀은 평범한 소년이지만, 어느 날 큰 모험에 휘말리게 된다. 그 모험 속에서 그는 진정한 용기와 우정을 배우게 된다. 성장과 모험의 서사시.",
                "흔해 빠진 일상 속에서 발견한 특별한 순간. 평범한 일상을 살아가는 사람들, 그들 사이에서 일어나는 작은 기적과 같은 이야기들. 그 평범함 속에서 발견하는 소중한 가치와 의미.",
                "인생은 한 번의 회전으로 바뀔 수 있다. 카지노에서 일어나는 한 게임, 그 게임이 한 사람의 인생을 송두리째 바꾸어 놓는다. 운명과 선택의 경계에서 벌어지는 긴박한 이야기.",
                "조선 시대, 전국을 떠돌며 이야기를 들려주는 전기수 '민욱'은 어느 날 운명처럼 한 소녀 '연희'를 만나게 된다. 연희는 민욱에게 자신의 가족이 겪은 억울한 사연을 이야기하고, 민욱은 그녀의 이야기를 전해 들으며 진실을 밝히기 위해 험난한 여정을 시작한다. 민중들의 사랑을 받던 전기수는 이제 진정한 정의를 찾기 위한 싸움을 시작하게 된다.",
                "천재 음악가 모차르트의 그늘에 가려진 또 다른 천재, 살리에르. 그가 겪는 질투와 경쟁, 그리고 내면의 갈등을 중심으로 이야기가 전개된다. 살리에르는 모차르트와의 관계 속에서 자신의 재능과 한계를 깨달으며, 결국 음악에 대한 진정한 열정을 되찾게 된다.",
                "중년 여성들이 겪는 갱년기 증상을 유쾌하고 코믹하게 그린 이야기. 네 명의 여성이 각자의 고민을 나누며 서로를 위로하고 지지하는 과정을 통해, 갱년기도 인생의 한 부분임을 깨닫고 새로운 희망을 찾는다.",
                "미스터리 소설의 여왕 아가사 크리스티의 삶을 다룬 이야기. 그녀가 소설을 쓰게 된 계기와, 각 작품에 숨겨진 비밀들, 그리고 그녀가 겪은 개인적인 어려움과 성공을 통해 진정한 작가의 모습을 조명한다.",
                "이탈리아의 작은 마을에서 성장한 두 형제의 이야기를 그린다. 형제는 각기 다른 길을 걷게 되지만, 가족의 소중함과 형제애를 통해 서로를 이해하고 다시 만나는 과정을 그린 감동적인 드라마.",
                "한적한 산속 오두막에서 벌어지는 공포와 서스펜스의 연속. 친구들과 함께 오두막을 찾은 주인공은 우연히 고대의 주문을 발견하게 되고, 이를 통해 악령이 깨어난다. 이제 생존을 위해 필사적으로 싸워야 하는 주인공들의 긴박한 이야기가 펼쳐진다.",
                "미래에 대한 막연한 꿈을 가진 청춘들의 이야기. 각기 다른 환경과 배경을 가진 네 명의 주인공이 우연히 만나 서로의 꿈을 응원하고, 함께 도전하며 성장해 나가는 과정을 그린다. '언젠가' 그들의 꿈이 현실이 되는 순간을 향해 나아가는 여정.",
                "연남동 한복판에 위치한 '연남장 캬바레'는 과거의 화려함을 간직한 채 오늘날에도 운영되고 있는 곳이다. 이곳에서 일하는 사람들과 손님들의 다양한 이야기가 얽히며, 캬바레의 무대는 그들의 인생을 비추는 거울이 된다. 서로 다른 삶이 교차하는 이곳에서, 그들은 꿈과 현실을 마주한다.",
                "다양한 예술가들이 모여 작품을 만들어가는 과정을 그린 이야기. 각자의 개성과 스타일이 다르지만, 함께 협력하며 하나의 작품을 완성해 나가는 과정을 통해 예술의 진정한 의미를 찾고, 서로의 열정을 인정하게 된다.",
                "첫사랑 김종욱을 찾아 나선 여인의 이야기. 그녀는 여러 단서를 추적하며 김종욱과의 추억을 되새기고, 그 과정에서 새로운 사랑과 인연을 만나게 된다. 첫사랑을 찾는 여정은 그녀에게 인생의 중요한 깨달음을 안겨준다.",
                "복수의 화신, 그녀의 검은 피를 부른다. 가족을 잃고 복수를 다짐한 여인 홍련, 그녀는 검을 들고 적들을 하나씩 쓰러뜨리며 자신의 운명을 개척해 나간다. 피로 물든 복수의 여정.",
                "화려한 불빛 속 숨겨진 진실. 화려한 클럽 드바이, 그곳에서 벌어지는 음모와 사랑, 그리고 배신의 이야기. 그 화려함 뒤에 숨겨진 어두운 진실을 파헤치는 사람들.",
                "얼음 위의 영원한 소년, 피터팬의 모험. 네버랜드에서 펼쳐지는 피터팬과 그의 친구들의 모험이 얼음 위에서 환상적으로 펼쳐진다. 아이스 스케이팅과 함께하는 동화 속 이야기.",
                "비밀스러운 정원에서 피어나는 희망. 부모를 잃고 외딴 저택으로 보내진 소녀 메리는 우연히 비밀의 화원을 발견한다. 그 화원에서 그녀는 새로운 친구들을 만나고, 희망을 되찾는다.",
                "감춰진 사랑, 폭발하는 감정. 종교적인 학교에서 벌어지는 청소년들의 사랑과 갈등, 그들의 감춰진 감정이 폭발하며 벌어지는 이야기. 억압된 감정들이 터져 나오는 순간.",
                "퇴근 후의 삶, 그 속에서 찾는 진정한 행복. 직장에서의 고된 하루가 끝나고, 퇴근 후의 시간 속에서 각자의 삶을 살아가는 사람들. 그 속에서 진정한 행복을 찾는 이야기.",
                "파티가 끝난 후, 진짜 이야기가 시작된다. 화려한 파티가 끝난 후, 그 뒤에 남겨진 사람들의 이야기. 파티의 흥청망청한 분위기 뒤에 숨겨진 진실과 갈등.",
                "마술사의 손끝에서 펼쳐지는 환상. 마술사 최현우가 선보이는 놀라운 마술 공연, 그의 손끝에서 펼쳐지는 환상적인 세계. 현실과 환상의 경계가 사라지는 순간.",
                "진실과 거짓의 경계에서 방황하는 소년. 에반 핸슨, 그는 우연한 거짓말로 인해 예상치 못한 상황에 처하게 된다. 그 거짓말이 가져온 파장 속에서 진정한 자신.",
                "연남동의 작은 골목에 자리한 '빙굴빙굴 빨래방'은 단순히 빨래를 하는 곳이 아니라, 동네 사람들의 애환과 사연이 얽힌 공간이다. 각기 다른 사연을 가진 사람들이 이곳에서 만나고, 서로의 이야기를 나누며 위로와 용기를 얻는다. 어느 날 빨래방을 운영하는 청년 민호는 우연히 찾아온 한 여인의 사연을 듣게 되는데, 그녀의 이야기는 민호의 인생을 바꿔놓는다.",
                "김태우가 전하는 감동의 메시지와 함께하는 수아레콘서트. 진솔한 이야기와 음악이 함께합니다.",
                "손병호와 최지연이 함께하는 여민락 콘서트, 세종시에서 펼쳐지는 특별한 무대입니다.",
                "자연 속에서 즐기는 예술 축제, 아트 포레스트 페스티벌에서 다양한 공연과 전시를 만나보세요.",
                "트로트 여왕 장윤정의 라이브 콘서트, 대구에서 열리는 그녀의 명곡들을 라이브로 즐겨보세요.",
                "아이돌 그룹 TREASURE의 리레이 투어 마지막 공연, 서울에서 펼쳐지는 대단원의 막을 함께하세요.",
                "시원한 맥주와 함께 즐기는 CassCool Festival, 다양한 음악 공연과 이벤트가 기다리고 있습니다.",
                "세계적인 팝스타 올리비아 로드리고의 첫 내한공연, 그녀의 감성적인 음악을 라이브로 만나보세요.",
                "유채훈의 단독 콘서트 'Sfumato', 그의 깊은 음악 세계를 느낄 수 있는 무대입니다.",
                "밴드 넬의 클럽 콘서트, 'Our Eutopia'에서 넬의 독특한 음악과 분위기를 경험해보세요.",
                "대구에서 열리는 'THE HYPER DAY' 페스티벌, 다양한 음악 장르와 함께하는 하루입니다.",
                "조선 시대, 전국을 떠돌며 이야기를 들려주는 전기수 '민욱'은 어느 날 운명처럼 한 소녀 '연희'를 만나게 된다. 연희는 민욱에게 자신의 가족이 겪은 억울한 사연을 이야기하고, 민욱은 그녀의 이야기를 전해 들으며 진실을 밝히기 위해 험난한 여정을 시작한다. 민중들의 사랑을 받던 전기수는 이제 진정한 정의를 찾기 위한 싸움을 시작하게 된다.",
                "천재 음악가 모차르트의 그늘에 가려진 또 다른 천재, 살리에르. 그가 겪는 질투와 경쟁, 그리고 내면의 갈등을 중심으로 이야기가 전개된다. 살리에르는 모차르트와의 관계 속에서 자신의 재능과 한계를 깨달으며, 결국 음악에 대한 진정한 열정을 되찾게 된다.",
                "중년 여성들이 겪는 갱년기 증상을 유쾌하고 코믹하게 그린 이야기. 네 명의 여성이 각자의 고민을 나누며 서로를 위로하고 지지하는 과정을 통해, 갱년기도 인생의 한 부분임을 깨닫고 새로운 희망을 찾는다.",
                "미스터리 소설의 여왕 아가사 크리스티의 삶을 다룬 이야기. 그녀가 소설을 쓰게 된 계기와, 각 작품에 숨겨진 비밀들, 그리고 그녀가 겪은 개인적인 어려움과 성공을 통해 진정한 작가의 모습을 조명한다.",
                "이탈리아의 작은 마을에서 성장한 두 형제의 이야기를 그린다. 형제는 각기 다른 길을 걷게 되지만, 가족의 소중함과 형제애를 통해 서로를 이해하고 다시 만나는 과정을 그린 감동적인 드라마.",
                "한적한 산속 오두막에서 벌어지는 공포와 서스펜스의 연속. 친구들과 함께 오두막을 찾은 주인공은 우연히 고대의 주문을 발견하게 되고, 이를 통해 악령이 깨어난다. 이제 생존을 위해 필사적으로 싸워야 하는 주인공들의 긴박한 이야기가 펼쳐진다.",
                "미래에 대한 막연한 꿈을 가진 청춘들의 이야기. 각기 다른 환경과 배경을 가진 네 명의 주인공이 우연히 만나 서로의 꿈을 응원하고, 함께 도전하며 성장해 나가는 과정을 그린다. '언젠가' 그들의 꿈이 현실이 되는 순간을 향해 나아가는 여정.",
                "연남동 한복판에 위치한 '연남장 캬바레'는 과거의 화려함을 간직한 채 오늘날에도 운영되고 있는 곳이다. 이곳에서 일하는 사람들과 손님들의 다양한 이야기가 얽히며, 캬바레의 무대는 그들의 인생을 비추는 거울이 된다. 서로 다른 삶이 교차하는 이곳에서, 그들은 꿈과 현실을 마주한다.",
                "다양한 예술가들이 모여 작품을 만들어가는 과정을 그린 이야기. 각자의 개성과 스타일이 다르지만, 함께 협력하며 하나의 작품을 완성해 나가는 과정을 통해 예술의 진정한 의미를 찾고, 서로의 열정을 인정하게 된다.",
                "첫사랑 김종욱을 찾아 나선 여인의 이야기. 그녀는 여러 단서를 추적하며 김종욱과의 추억을 되새기고, 그 과정에서 새로운 사랑과 인연을 만나게 된다. 첫사랑을 찾는 여정은 그녀에게 인생의 중요한 깨달음을 안겨준다.",
                "조선 시대, 전국을 떠돌며 이야기를 들려주는 전기수 '민욱'은 어느 날 운명처럼 한 소녀 '연희'를 만나게 된다. 연희는 민욱에게 자신의 가족이 겪은 억울한 사연을 이야기하고, 민욱은 그녀의 이야기를 전해 들으며 진실을 밝히기 위해 험난한 여정을 시작한다. 민중들의 사랑을 받던 전기수는 이제 진정한 정의를 찾기 위한 싸움을 시작하게 된다.",
                "천재 음악가 모차르트의 그늘에 가려진 또 다른 천재, 살리에르. 그가 겪는 질투와 경쟁, 그리고 내면의 갈등을 중심으로 이야기가 전개된다. 살리에르는 모차르트와의 관계 속에서 자신의 재능과 한계를 깨달으며, 결국 음악에 대한 진정한 열정을 되찾게 된다.",
                "중년 여성들이 겪는 갱년기 증상을 유쾌하고 코믹하게 그린 이야기. 네 명의 여성이 각자의 고민을 나누며 서로를 위로하고 지지하는 과정을 통해, 갱년기도 인생의 한 부분임을 깨닫고 새로운 희망을 찾는다.",
                "미스터리 소설의 여왕 아가사 크리스티의 삶을 다룬 이야기. 그녀가 소설을 쓰게 된 계기와, 각 작품에 숨겨진 비밀들, 그리고 그녀가 겪은 개인적인 어려움과 성공을 통해 진정한 작가의 모습을 조명한다.",
                "이탈리아의 작은 마을에서 성장한 두 형제의 이야기를 그린다. 형제는 각기 다른 길을 걷게 되지만, 가족의 소중함과 형제애를 통해 서로를 이해하고 다시 만나는 과정을 그린 감동적인 드라마.",
                "한적한 산속 오두막에서 벌어지는 공포와 서스펜스의 연속. 친구들과 함께 오두막을 찾은 주인공은 우연히 고대의 주문을 발견하게 되고, 이를 통해 악령이 깨어난다. 이제 생존을 위해 필사적으로 싸워야 하는 주인공들의 긴박한 이야기가 펼쳐진다.",
                "미래에 대한 막연한 꿈을 가진 청춘들의 이야기. 각기 다른 환경과 배경을 가진 네 명의 주인공이 우연히 만나 서로의 꿈을 응원하고, 함께 도전하며 성장해 나가는 과정을 그린다. '언젠가' 그들의 꿈이 현실이 되는 순간을 향해 나아가는 여정.",
                "연남동 한복판에 위치한 '연남장 캬바레'는 과거의 화려함을 간직한 채 오늘날에도 운영되고 있는 곳이다. 이곳에서 일하는 사람들과 손님들의 다양한 이야기가 얽히며, 캬바레의 무대는 그들의 인생을 비추는 거울이 된다. 서로 다른 삶이 교차하는 이곳에서, 그들은 꿈과 현실을 마주한다.",
                "다양한 예술가들이 모여 작품을 만들어가는 과정을 그린 이야기. 각자의 개성과 스타일이 다르지만, 함께 협력하며 하나의 작품을 완성해 나가는 과정을 통해 예술의 진정한 의미를 찾고, 서로의 열정을 인정하게 된다.",
                "첫사랑 김종욱을 찾아 나선 여인의 이야기. 그녀는 여러 단서를 추적하며 김종욱과의 추억을 되새기고, 그 과정에서 새로운 사랑과 인연을 만나게 된다. 첫사랑을 찾는 여정은 그녀에게 인생의 중요한 깨달음을 안겨준다.",
                "2024 전주 얼티밋 뮤직 페스티벌 사전예매, 최고의 뮤지션들과 함께하는 음악 축제.",
                "손태진과 에녹이 함께하는 수원 콘서트, 두 아티스트의 환상적인 하모니를 느껴보세요.",
                "미스트롯3 전국투어 콘서트, 천안에서 열리는 트로트의 향연.",
                "심수봉의 전국투어 콘서트 '꽃길', 성남에서 펼쳐지는 감동의 무대.",
                "2024 장우영 팬미팅 'Be Young', 팬들과 함께하는 특별한 시간.",
                "밴드 소란의 여름 콘서트 'Squeeze!', 신나는 여름밤을 함께하세요.",
                "팝스타 코난 그레이의 내한공연, 그의 히트곡을 라이브로 즐길 수 있는 기회.",
                "2024 HAVE A NICE TRIP, 다양한 아티스트들과 함께하는 여행 같은 음악 축제.",
                "2024 춘천공연예술제, 반도와 그레이바이실버가 함께하는 특별한 음악 공연.",
                "2024 춘천공연예술제, 빅바플, 일렉트릭플라워즈, 수에뇨가 함께하는 다채로운 음악의 향연.",
                "2024 전주 얼티밋 뮤직 페스티벌 사전예매, 최고의 뮤지션들과 함께하는 음악 축제.",
                "손태진과 에녹이 함께하는 수원 콘서트, 두 아티스트의 환상적인 하모니를 느껴보세요.",
                "미스트롯3 전국투어 콘서트, 천안에서 열리는 트로트의 향연.",
                "심수봉의 전국투어 콘서트 '꽃길', 성남에서 펼쳐지는 감동의 무대.",
                "2024 장우영 팬미팅 'Be Young', 팬들과 함께하는 특별한 시간.",
                "밴드 소란의 여름 콘서트 'Squeeze!', 신나는 여름밤을 함께하세요.",
                "팝스타 코난 그레이의 내한공연, 그의 히트곡을 라이브로 즐길 수 있는 기회.",
                "2024 HAVE A NICE TRIP, 다양한 아티스트들과 함께하는 여행 같은 음악 축제.",
                "2024 춘천공연예술제, 반도와 그레이바이실버가 함께하는 특별한 음악 공연.",
                "2024 춘천공연예술제, 빅바플, 일렉트릭플라워즈, 수에뇨가 함께하는 다채로운 음악의 향연.",
                "뮤지컬 배우 마이클 리가 안양에서 그의 가장 사랑받는 곡들을 선보이는 단독 콘서트입니다.",
                "뮤지컬 배우 마이클 리가 안양에서 그의 가장 사랑받는 곡들을 선보이는 단독 콘서트입니다.",
                "신인류가 부산에서 펼치는 화려한 단독 콘서트, '푸른 열대'의 뜨거운 무대를 기대하세요.",
                "인기 아이돌 그룹 BABYMONSTER가 서울에서 팬들과 함께하는 특별한 공연입니다.",
                "노엘 갤러거가 이끄는 하이 플라잉 버즈의 강렬한 라이브 공연을 경험해보세요.",
                "싸이의 여름 대표 공연, 흠뻑쇼가 속초에서 펼쳐집니다. 물폭탄과 함께하는 열정적인 무대!",
                "팝스타 두아 리파의 첫 내한공연, 그녀의 히트곡들을 라이브로 즐길 수 있는 기회입니다.",
                "혁오와 선셋 롤러코스터가 함께하는 AAA 투어, 두 밴드의 매력적인 음악을 만끽하세요.",
                "팝스타 코난 그레이의 내한공연, 그의 히트곡을 라이브로 즐길 수 있는 기회.",
                "2024 HAVE A NICE TRIP, 다양한 아티스트들과 함께하는 여행 같은 음악 축제.",
                "뮤지컬 배우 마이클 리가 안양에서 그의 가장 사랑받는 곡들을 선보이는 단독 콘서트입니다.",
                "뮤지컬 배우 마이클 리가 안양에서 그의 가장 사랑받는 곡들을 선보이는 단독 콘서트입니다.",
                "신인류가 부산에서 펼치는 화려한 단독 콘서트, '푸른 열대'의 뜨거운 무대를 기대하세요.",
                "인기 아이돌 그룹 BABYMONSTER가 서울에서 팬들과 함께하는 특별한 공연입니다.",
                "노엘 갤러거가 이끄는 하이 플라잉 버즈의 강렬한 라이브 공연을 경험해보세요.",
                "싸이의 여름 대표 공연, 흠뻑쇼가 속초에서 펼쳐집니다. 물폭탄과 함께하는 열정적인 무대!",
                "팝스타 두아 리파의 첫 내한공연, 그녀의 히트곡들을 라이브로 즐길 수 있는 기회입니다.",
                "혁오와 선셋 롤러코스터가 함께하는 AAA 투어, 두 밴드의 매력적인 음악을 만끽하세요.",
                "팝스타 코난 그레이의 내한공연, 그의 히트곡을 라이브로 즐길 수 있는 기회.",
                "2024 HAVE A NICE TRIP, 다양한 아티스트들과 함께하는 여행 같은 음악 축제.",
                "김태우가 전하는 감동의 메시지와 함께하는 수아레콘서트. 진솔한 이야기와 음악이 함께합니다.",
                "손병호와 최지연이 함께하는 여민락 콘서트, 세종시에서 펼쳐지는 특별한 무대입니다.",
                "자연 속에서 즐기는 예술 축제, 아트 포레스트 페스티벌에서 다양한 공연과 전시를 만나보세요.",
                "트로트 여왕 장윤정의 라이브 콘서트, 대구에서 열리는 그녀의 명곡들을 라이브로 즐겨보세요.",
                "아이돌 그룹 TREASURE의 리레이 투어 마지막 공연, 서울에서 펼쳐지는 대단원의 막을 함께하세요.",
                "시원한 맥주와 함께 즐기는 CassCool Festival, 다양한 음악 공연과 이벤트가 기다리고 있습니다.",
                "세계적인 팝스타 올리비아 로드리고의 첫 내한공연, 그녀의 감성적인 음악을 라이브로 만나보세요.",
                "유채훈의 단독 콘서트 'Sfumato', 그의 깊은 음악 세계를 느낄 수 있는 무대입니다.",
                "밴드 넬의 클럽 콘서트, 'Our Eutopia'에서 넬의 독특한 음악과 분위기를 경험해보세요.",
                "대구에서 열리는 'THE HYPER DAY' 페스티벌, 다양한 음악 장르와 함께하는 하루입니다.",
                "김태우가 전하는 감동의 메시지와 함께하는 수아레콘서트. 진솔한 이야기와 음악이 함께합니다.",
                "손병호와 최지연이 함께하는 여민락 콘서트, 세종시에서 펼쳐지는 특별한 무대입니다.",
                "자연 속에서 즐기는 예술 축제, 아트 포레스트 페스티벌에서 다양한 공연과 전시를 만나보세요.",
                "트로트 여왕 장윤정의 라이브 콘서트, 대구에서 열리는 그녀의 명곡들을 라이브로 즐겨보세요.",
                "아이돌 그룹 TREASURE의 리레이 투어 마지막 공연, 서울에서 펼쳐지는 대단원의 막을 함께하세요.",
                "시원한 맥주와 함께 즐기는 CassCool Festival, 다양한 음악 공연과 이벤트가 기다리고 있습니다.",
                "세계적인 팝스타 올리비아 로드리고의 첫 내한공연, 그녀의 감성적인 음악을 라이브로 만나보세요.",
                "유채훈의 단독 콘서트 'Sfumato', 그의 깊은 음악 세계를 느낄 수 있는 무대입니다.",
                "밴드 넬의 클럽 콘서트, 'Our Eutopia'에서 넬의 독특한 음악과 분위기를 경험해보세요.",
                "대구에서 열리는 'THE HYPER DAY' 페스티벌, 다양한 음악 장르와 함께하는 하루입니다."
        };
        Long[] viewCounts = {
                59L,
                20L,
                50L,
                34L,
                52L,
                16L,
                25L,
                35L,
                12L,
                5L,
                27L,
                21L,
                41L,
                56L,
                69L,
                28L,
                11L,
                68L,
                94L,
                38L,
                58L,
                19L,
                49L,
                33L,
                51L,
                15L,
                24L,
                34L,
                11L,
                4L,
                26L,
                20L,
                40L,
                55L,
                68L,
                27L,
                10L,
                67L,
                93L,
                37L,
                62L,
                23L,
                53L,
                37L,
                55L,
                19L,
                28L,
                38L,
                15L,
                8L,
                30L,
                24L,
                44L,
                59L,
                72L,
                31L,
                14L,
                71L,
                97L,
                41L,
                55L,
                16L,
                46L,
                30L,
                48L,
                12L,
                21L,
                31L,
                8L,
                1L,
                23L,
                17L,
                37L,
                52L,
                65L,
                24L,
                7L,
                64L,
                90L,
                34L,
                53L,
                14L,
                44L,
                28L,
                46L,
                10L,
                19L,
                29L,
                6L,
                3L,
                21L,
                15L,
                35L,
                50L,
                63L,
                22L,
                5L,
                62L,
                88L,
                32L,
                67L,
                28L,
                58L,
                42L,
                60L,
                24L,
                33L,
                43L,
                20L,
                13L,
                35L,
                29L,
                49L,
                64L,
                77L,
                36L,
                19L,
                76L,
                102L,
                46L,
                79L,
                40L,
                70L,
                54L,
                72L,
                36L,
                45L,
                55L,
                32L,
                25L,
                47L,
                41L,
                61L,
                76L,
                89L,
                48L,
                31L,
                88L,
                114L,
                58L
        };
//        0~139
        System.out.println(urls.length);
        System.out.println(titles.length);
        System.out.println(descriptions.length);
        System.out.println(viewCounts.length);
        GenreType[] genreValues = GenreType.values(); //0~19 , 20~39 ...
        LocalDate today = LocalDate.now();
        LocalDateTime todayTime = today.atTime(LocalTime.of(19, 0, 0));
        AgeGroup ageGroup = AgeGroup.ALL;
        Integer runTime = 100;

        Venue venue1 = venueRepository.findById(1L).get();
        Venue venue2 = venueRepository.findById(2L).get();

        List<Performance> performanceList = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            String imageUrl = urls[i];
            String title = titles[i];
            String description = descriptions[i];
            Long viewCount = viewCounts[i];

            LocalDateTime openAt = ((i / 10) % 2 == 0) ? todayTime : todayTime.plusDays(1);
            LocalDate startAt = openAt.toLocalDate().plusDays(7);
            LocalDate endAt = startAt.plusDays(1);

            Venue venue = ((i / 20) % 2 == 0) ? venue1 : venue2;
            GenreType genreType = genreValues[i / 20];
            Long id = Long.valueOf((long) i) + 1L;
            performanceList.add(new Performance(id, venue, title, description, genreType, ageGroup, runTime, openAt, startAt, endAt, imageUrl, viewCount, 0L));

        }
        performanceRepository.saveAll(performanceList);
    }


    @Test
    @Order(3)
    @Transactional()
    @Rollback(value = false)
    void createSalesDummyData() {
        // 1~10, 21~30, 41~50
        List<Long> performanceIdList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = 1; j < 11; j++) {
                performanceIdList.add(Long.valueOf(20 * i + j));
            }
        }

        List<Performance> performanceList = performanceRepository.findAllById(performanceIdList);

        for (int i = 0; i < performanceList.size(); i++) {
            int discountRate = switch (i % 10) {
                case 0, 1, 2 -> 10;
                case 3, 4, 5 -> 20;
                case 6, 7, 8 -> 30;
                default -> 40;
            };

            Long id = Long.valueOf(i + 1);

            // 오픈날짜로부터 하루만 세일, saleEndDate 수정하면 늘릴 수 있음
            LocalDate saleStartDate = performanceList.get(i).getOpenAt().toLocalDate();
            LocalDateTime saleStartDateTime = saleStartDate.atTime(LocalTime.of(12, 0, 0));
            LocalDate saleEndDate = saleStartDate.plusDays(1);
            LocalDateTime saleEndDateTime = saleEndDate.atTime(LocalTime.of(12, 0, 0));

            Sales sales = new Sales(id, performanceList.get(i), discountRate, saleStartDateTime, saleEndDateTime);
            salesRepository.save(sales);
        }
    }


    @Test
    @Order(4)
    @Transactional()
    @Rollback(value = false)
    void createSessionDummyData() {
        List<Performance> performanceList = performanceRepository.findAll();
        long id = 1;
        List<Session> sessionList = new ArrayList<>();
        for (int i = 0; i < performanceList.size(); i++) {
            Performance performance = performanceList.get(i);
            LocalDate startAt = performance.getStartAt();
            LocalDate endAt = performance.getEndAt();
            long until = startAt.until(endAt, ChronoUnit.DAYS);


            for (int j = 0; j <= until; j++) {
                LocalDate sessionDate = startAt.plusDays(j);
                LocalTime sessionTime1 = LocalTime.of(12, 0, 0);
                LocalTime sessionTime2 = LocalTime.of(18, 0, 0);

                // 공연별로, 하루에 세션 2개씩 생성
                Session aSession = new Session(id++, performance, sessionDate, sessionTime1, "A");
                Session bSession = new Session(id++, performance, sessionDate, sessionTime2, "B");
                sessionList.add(aSession);
                sessionList.add(bSession);
            }
        }
        sessionRepository.saveAll(sessionList);
    }


    @Test
    @Order(5)
    @Transactional()
    @Rollback(value = false)
    void createSeatDummyData() {
        // 세션별로, 공연의 공연장 정보를 찾아서, 공연장의 totalSeatCount 만큼 시트를 생성.
        List<Session> sessionList = sessionRepository.findAll();
        int[] prices = {150000, 130000, 110000, 90000};
        SeatGrade[] seatGrades = SeatGrade.values();


        List<Seat> seatList = new ArrayList<>();
        long id = 1;
        for (Session session : sessionList) {
            Performance performance = session.getPerformance();
            Venue venue = performance.getVenue();

            int price = 0;
            SeatGrade seatGrade = SeatGrade.S;
            for (int i = 0; i < venue.getTotalSeatCount(); i++) {
                String seatNumber = "" + (i % (venue.getTotalSeatCount() / 4) + 1);
                double temp = venue.getTotalSeatCount() / 4;
                if (i < temp) {
                    price = prices[0];
                    seatGrade = seatGrades[0];
                } else if (i < temp * 2) {
                    price = prices[1];
                    seatGrade = seatGrades[1];
                } else if (i < temp * 3) {
                    price = prices[2];
                    seatGrade = seatGrades[2];
                } else {
                    price = prices[3];
                    seatGrade = seatGrades[3];
                }

                seatList.add(new Seat(id++, session, price, seatNumber, seatGrade, SeatStatus.NOT_RESERVED, null));
            }
        }
        seatRepository.saveAll(seatList);
    }

    @Test
    @Order(6)
    @Transactional()
    @Rollback(value = false)
    void createUserDummyData() {
        String userStr = "user";
        String admin = "admin";
        String password = "qwer1234";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        List<User> userList = new ArrayList<>();

        // admin 3명
        for (int i = 1; i <= 3; i++) {
            UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
                    admin + i + "@gmail.com",
                    "qwer1234",
                    admin + i,
                    admin + i,
                    "010-0000-0000",
                    "서울특별시 강남구 테헤란로44길 8"
            );

            userList.add(new User(userSignupRequestDto, encodedPassword, UserRole.ADMIN));
        }

        // user 7명
        for (int i = 1; i <= 7; i++) {
            UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
                    userStr + i + "@gmail.com",
                    "qwer1234",
                    userStr + i,
                    userStr + i,
                    "010-0000-0000",
                    "서울특별시 강남구 테헤란로44길 8"
            );

            userList.add(new User(userSignupRequestDto, encodedPassword, UserRole.USER));

        }
        userRepository.saveAll(userList);
        System.out.println();
    }

    @Test
    @Order(7)
    @Transactional()
    @Rollback(value = false)
    void createBannerDummyData(){
        List<Banner> bannerList = new ArrayList<>();
        bannerList.add(new Banner(1L, 1, "performances/97", BannerType.MAIN, "https://tickets.interpark.com/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FNMain%2FBbannerPC%2F2407%2F240710051215_16007528.gif&w=1920&q=75"));
        bannerList.add(new Banner(2L, 2, "performances/22", BannerType.MAIN, "https://tickets.interpark.com/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FNMain%2FBbannerPC%2F2406%2F240610015204_24007345.gif&w=3840&q=75"));
        bannerList.add(new Banner(3L, 3, "performances/83", BannerType.MAIN, "https://tickets.interpark.com/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FNMain%2FBbannerPC%2F2406%2F240617041354_24002890.gif&w=3840&q=75"));
        bannerList.add(new Banner(4L, 4, "performances/106", BannerType.MAIN, "https://tickets.interpark.com/_next/image?url=http%3A%2F%2Fticketimage.interpark.com%2FTCMS3.0%2FNMain%2FBbannerPC%2F2406%2F240610094556_16007528.gif&w=3840&q=75"));
        bannerList.add(new Banner(5L, 1, "performances/106", BannerType.MIDDLE, "https://ifh.cc/g/DG5q0N.png"));
        bannerList.add(new Banner(6L, 2, "performances/106", BannerType.MIDDLE, "https://ifh.cc/g/Bc5XCX.png"));
        bannerList.add(new Banner(7L, 1, "performances/106", BannerType.BOTTOM, "https://ifh.cc/g/dHgYbn.png"));
        bannerList.add(new Banner(8L, 2, "performances/106", BannerType.BOTTOM, "https://ifh.cc/g/0vo3Tm.png"));
        bannerRepository.saveAll(bannerList);
    }
}