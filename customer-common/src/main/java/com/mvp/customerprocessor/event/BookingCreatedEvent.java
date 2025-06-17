package com.mvp.customerprocessor.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingCreatedEvent extends Event {
    private UUID customerUid;
    private Long restaurantId;
    private String restaurantName;
    private Integer placeCapacity;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
}
