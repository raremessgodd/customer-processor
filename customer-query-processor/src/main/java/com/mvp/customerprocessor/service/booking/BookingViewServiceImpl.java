package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.dto.BookingViewDto;
import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import com.mvp.customerprocessor.mapper.BookingViewMapper;
import com.mvp.customerprocessor.repository.BookingViewRepository;
import com.mvp.customerprocessor.util.Constraints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingViewServiceImpl implements BookingViewService {

    private final BookingViewRepository bookingViewRepository;
    private final BookingViewMapper bookingViewMapper;

    @Override
    @CacheEvict(cacheNames = Constraints.BOOKING_CUSTOMER_ID_CACHE_NAME, key = "#customerUid")
    public List<BookingViewDto> getByCustomer(UUID customerUid) {
        return bookingViewRepository.findByCustomerUid(customerUid).stream()
                .map(bookingViewMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingViewDto> getByRestaurantAndDate(Long restaurantId, LocalDate date) {
        if (date == null) {
            return bookingViewRepository.findByRestaurantId(restaurantId).stream()
                    .map(bookingViewMapper::toDto)
                    .toList();
        }
        return bookingViewRepository.findByRestaurantIdAndBookingDate(restaurantId, date).stream()
                .map(bookingViewMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingViewDto> getConflictingBookings(Long restaurantId, LocalDateTime startTime, LocalDateTime endTime) {
        return bookingViewRepository.findConflictingBookings(restaurantId, startTime, endTime).stream()
                .map(bookingViewMapper::toDto)
                .toList();
    }

    @Override
    @CacheEvict(cacheNames = Constraints.BOOKING_ID_CACHE_NAME, key = "#bookingUid")
    public BookingViewDto getById(UUID bookingUid) {
        return bookingViewRepository.findByBookingUid(bookingUid)
                .map(bookingViewMapper::toDto)
                .orElseThrow(() -> new CustomerProcessorRuntimeException("Не найдено бронирование с uid=" + bookingUid));
    }
}
