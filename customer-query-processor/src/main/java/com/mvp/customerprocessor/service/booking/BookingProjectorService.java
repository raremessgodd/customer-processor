package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;

public interface BookingProjectorService {

    void save(BookingCreatedEvent bookingCreatedEvent);

    void complete(BookingCompletedEvent bookingCompletedEvent);

    void cancel(BookingCancelledEvent bookingCancelledEvent);

    void modify(BookingModifiedEvent bookingModifiedEvent);
}
