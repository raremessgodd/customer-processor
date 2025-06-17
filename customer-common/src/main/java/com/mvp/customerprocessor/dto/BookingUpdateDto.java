package com.mvp.customerprocessor.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record BookingUpdateDto(
        @Positive Integer placeCapacity,
        @FutureOrPresent LocalDateTime bookingStartTime,
        @FutureOrPresent LocalDateTime bookingEndTime
) implements Serializable {
    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        return bookingEndTime == null || bookingStartTime == null || bookingEndTime.isAfter(bookingStartTime);
    }
}
