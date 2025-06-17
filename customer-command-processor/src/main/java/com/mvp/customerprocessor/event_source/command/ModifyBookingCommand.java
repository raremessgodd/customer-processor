package com.mvp.customerprocessor.event_source.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ModifyBookingCommand extends Command {
    private UUID bookingUid;
    private Integer placeCapacity;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;
}
