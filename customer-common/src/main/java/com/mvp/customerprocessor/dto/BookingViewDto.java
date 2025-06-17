package com.mvp.customerprocessor.dto;

import com.mvp.customerprocessor.enums.BookingEventType;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record BookingViewDto(
        String id,
        UUID bookingUid,
        UUID customerUid,
        Long restaurantId,
        String restaurantName,
        Integer placeCapacity,
        LocalDateTime bookingStartTime,
        LocalDateTime bookingEndTime,
        BookingEventType status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDate bookingDate,
        String bookingDayOfWeek
) implements Serializable {
}
