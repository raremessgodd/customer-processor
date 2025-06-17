package com.mvp.customerprocessor.controller;

import com.mvp.customerprocessor.dto.BookingViewDto;
import com.mvp.customerprocessor.service.booking.BookingViewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingViewController {

    private final BookingViewService bookingViewService;

    @GetMapping("/customer/{customerUid}")
    public List<BookingViewDto> getCustomerBookings(@PathVariable UUID customerUid) {
        return bookingViewService.getByCustomer(customerUid);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<BookingViewDto> getRestaurantBookings(
            @PathVariable @Valid @Positive Long restaurantId,
            @RequestParam(required = false) LocalDate date
    ) {
        return bookingViewService.getByRestaurantAndDate(restaurantId, date);
    }

    @GetMapping("/{bookingUid}")
    public BookingViewDto getBookingDetails(@PathVariable UUID bookingUid) {
        return bookingViewService.getById(bookingUid);
    }

    @GetMapping("/restaurant/{restaurantId}/conflicts")
    public List<BookingViewDto> checkTimeSlotAvailability(
            @PathVariable @Valid @Positive Long restaurantId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime
    ) {

        return bookingViewService.getConflictingBookings(restaurantId, startTime, endTime);
    }
}
