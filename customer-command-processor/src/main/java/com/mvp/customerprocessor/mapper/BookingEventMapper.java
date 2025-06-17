package com.mvp.customerprocessor.mapper;

import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
import com.mvp.customerprocessor.event_source.command.CancelBookingCommand;
import com.mvp.customerprocessor.event_source.command.CompleteBookingCommand;
import com.mvp.customerprocessor.event_source.command.CreateBookingCommand;
import com.mvp.customerprocessor.event_source.command.ModifyBookingCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookingEventMapper {

    @Mappings({
            @Mapping(source = "createBookingCommand.commandUid", target = "aggregateUid"),
            @Mapping(source = "createBookingCommand.userUid", target = "customerUid"),
            @Mapping(target = "version", constant = "1"),
    })
    BookingCreatedEvent toCreateEvent(CreateBookingCommand createBookingCommand, UUID eventUid);

    @Mappings({
            @Mapping(source = "cancelBookingCommand.bookingUid", target = "aggregateUid")
    })
    BookingCancelledEvent toCancelEvent(CancelBookingCommand cancelBookingCommand, int version, UUID eventUid);

    @Mappings({
            @Mapping(source = "completeBookingCommand.bookingUid", target = "aggregateUid")
    })
    BookingCompletedEvent toCompleteEvent(CompleteBookingCommand completeBookingCommand, int version, UUID eventUid);

    @Mappings({
            @Mapping(source = "modifyBookingCommand.bookingUid", target = "aggregateUid")
    })
    BookingModifiedEvent toModifyEvent(ModifyBookingCommand modifyBookingCommand, int version, UUID eventUid);
}
