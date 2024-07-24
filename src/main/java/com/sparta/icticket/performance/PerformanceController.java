package com.sparta.icticket.performance;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.performance.dto.PerformanceDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    /**
     * 단일 공연 조회
     * @param performanceId
     * @return
     */
    @GetMapping("/{performanceId}")
    public ResponseEntity<ResponseDataDto<PerformanceDetailResponseDto>> getPerformance(
            @PathVariable Long performanceId) {

        PerformanceDetailResponseDto responseDto = performanceService.getPerformance(performanceId);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.PERFORMANCE_GET_INFO_SUCCESS, responseDto));
    }

    /**
     * 장르별 공연 랭킹 조회
     * @param genre
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/ranking")
    public ResponseEntity<ResponseDataDto<List<PerformanceDetailResponseDto>>> getGenreRankPerformances(
            @RequestParam(value = "genre") GenreType genre,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){

        List<PerformanceDetailResponseDto> responseDto = performanceService.getGenreRankPerformances(genre, page-1, size);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.PERFORMANCE_GET_GENRE_RANKING_SUCCESS, responseDto));
    }

    /**
     * 오늘 오픈 공연 조회
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/today-open")
    public ResponseEntity<ResponseDataDto<List<PerformanceDetailResponseDto>>> getTodayOpenPerformances(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<PerformanceDetailResponseDto> responseDtoPage = performanceService.getTodayOpenPerformances(page-1, size);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.PERFORMANCE_GET_TODAY_OPEN_SUCCESS, responseDtoPage));
    }

    /**
     * 할인 중인 공연 조회
     * @param genre
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/genre/discount")
    public ResponseEntity<ResponseDataDto<List<PerformanceDetailResponseDto>>> getDiscountPerformances(
            @RequestParam(value = "genre") GenreType genre,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size){

        List<PerformanceDetailResponseDto> responseDtoPage = performanceService.getDiscountPerformances(genre, page-1, size);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.PERFORMANCE_GET_DISCOUNT_SUCCESS, responseDtoPage));
    }

    /**
     * 오픈 예정 공연 조회
     * @param genre
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/genre/will-be-opened")
    public ResponseEntity<ResponseDataDto<List<PerformanceDetailResponseDto>>> getWillBeOpenedPerformances(
            @RequestParam(value = "genre") GenreType genre,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        List<PerformanceDetailResponseDto> responseDtoPage = performanceService.getWillBeOpenedPerformances(genre, page-1, size);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.PERFORMANCE_GET_WILL_BE_OPEN_SUCCESS, responseDtoPage));
    }
}
