package com.mvp.customerprocessor.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record BookingCreateDto(
        @NotNull @Positive Long restaurantId,
        @NotNull @Positive Integer placeCapacity,
        @NotNull @FutureOrPresent LocalDateTime bookingStartTime,
        @NotNull @FutureOrPresent LocalDateTime bookingEndTime
) implements Serializable {
    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        return bookingEndTime == null || bookingStartTime == null || bookingEndTime.isAfter(bookingStartTime);
    }
}
