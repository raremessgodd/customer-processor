package com.mvp.customerprocessor.controller;

import com.mvp.customerprocessor.dto.BookingCreateDto;
import com.mvp.customerprocessor.dto.BookingUpdateDto;
import com.mvp.customerprocessor.service.booking.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public UUID createBooking(@RequestBody @Valid BookingCreateDto bookingEventCreateDto) {
        return bookingService.createBooking(bookingEventCreateDto);
    }

    @DeleteMapping("/{bookingUid}")
    public void cancelBooking(
            @PathVariable UUID bookingUid
    ) {
        bookingService.cancelBooking(bookingUid);
    }

    @PutMapping("/{bookingUid}/complete")
    public void completeBooking(
            @PathVariable UUID bookingUid
    ) {
        bookingService.completeBooking(bookingUid);
    }

    @PutMapping("/{bookingUid}")
    public void modifyBooking(
            @PathVariable UUID bookingUid,
            @RequestBody @Valid BookingUpdateDto bookingUpdateDto
    ) {
        bookingService.modifyBooking(bookingUpdateDto, bookingUid);
    }
}
