package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.dto.BookingCreateDto;
import com.mvp.customerprocessor.dto.BookingUpdateDto;

import java.util.UUID;

public interface BookingService {

    UUID createBooking(BookingCreateDto bookingCreateDto);

    void cancelBooking(UUID bookingUid);

    void completeBooking(UUID bookingUid);

    void modifyBooking(BookingUpdateDto bookingUpdateDto, UUID bookingUid);
}
