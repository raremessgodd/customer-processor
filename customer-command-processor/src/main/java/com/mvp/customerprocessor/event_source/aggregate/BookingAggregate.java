package com.mvp.customerprocessor.event_source.aggregate;

import com.mvp.customerprocessor.enums.BookingEventType;
import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BookingAggregate {

    private final UUID bookingUid;
    private UUID customerUid;
    private Long restaurantId;
    private Integer placeCapacity;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
    private BookingEventType status;
    private int version;

    public BookingAggregate(UUID bookingUid) {
        this.bookingUid = bookingUid;
    }

    public void apply(BookingCreatedEvent event) {
        this.customerUid = event.getCustomerUid();
        this.restaurantId = event.getRestaurantId();
        this.placeCapacity = event.getPlaceCapacity();
        this.bookingStartTime = event.getBookingStartTime();
        this.bookingEndTime = event.getBookingEndTime();
        this.status = BookingEventType.CREATE;
        this.version = 1;
    }

    public void apply(BookingCancelledEvent event) {
        this.status = BookingEventType.CANCEL;
        this.version++;
    }

    public void apply(BookingCompletedEvent event) {
        this.status = BookingEventType.COMPLETE;
        this.version++;
    }

    public void apply(BookingModifiedEvent event) {
        if (event.getPlaceCapacity() != null) {
            this.placeCapacity = event.getPlaceCapacity();
        }
        if (event.getBookingStartTime() != null) {
            this.bookingStartTime = event.getBookingStartTime();
        }
        if (event.getBookingEndTime() != null) {
            this.bookingEndTime = event.getBookingEndTime();
        }
        this.status = BookingEventType.MODIFY;
        this.version++;
    }
}
