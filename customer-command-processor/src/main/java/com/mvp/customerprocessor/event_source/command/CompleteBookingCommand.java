package com.mvp.customerprocessor.event_source.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CompleteBookingCommand extends Command {
    private UUID bookingUid;
}
