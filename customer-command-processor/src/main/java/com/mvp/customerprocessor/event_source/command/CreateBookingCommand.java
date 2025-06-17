package com.mvp.customerprocessor.event_source.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreateBookingCommand extends Command {
    private Long restaurantId;
    private String restaurantName;
    private Integer placeCapacity;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
}
