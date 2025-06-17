package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.dto.BookingViewDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingViewService {

    List<BookingViewDto> getByCustomer(UUID customerUid);

    List<BookingViewDto> getByRestaurantAndDate(Long restaurantId, LocalDate date);

    List<BookingViewDto> getConflictingBookings(Long restaurantId, LocalDateTime startTime, LocalDateTime endTime);

    BookingViewDto getById(UUID bookingUid);
}
