package com.mvp.customerprocessor.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingModifiedEvent extends Event {
    private Integer placeCapacity;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
}
