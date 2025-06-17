package com.mvp.customerprocessor.mapper;

import com.mvp.customerprocessor.entity.BookingEventStore;
import com.mvp.customerprocessor.event_source.command.CancelBookingCommand;
import com.mvp.customerprocessor.event_source.command.CompleteBookingCommand;
import com.mvp.customerprocessor.event_source.command.CreateBookingCommand;
import com.mvp.customerprocessor.event_source.command.ModifyBookingCommand;
import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
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
public interface BookingEventStoreMapper {

    @Mappings({
            @Mapping(source = "restaurantId", target = "restaurant.id"),
            @Mapping(source = "userUid", target = "customerUid"),
            @Mapping(source = "commandUid", target = "aggregateUid"),
            @Mapping(target = "version", constant = "1")
    })
    BookingEventStore toEventStore(CreateBookingCommand createBookingCommand);

    @Mappings({
            @Mapping(source = "cancelBookingCommand.userUid", target = "customerUid"),
            @Mapping(source = "cancelBookingCommand.bookingUid", target = "aggregateUid")
    })
    BookingEventStore toEventStore(CancelBookingCommand cancelBookingCommand, int version);

    @Mappings({
            @Mapping(source = "completeBookingCommand.userUid", target = "customerUid"),
            @Mapping(source = "completeBookingCommand.bookingUid", target = "aggregateUid")
    })
    BookingEventStore toEventStore(CompleteBookingCommand completeBookingCommand, int version);

    @Mappings({
            @Mapping(source = "modifyBookingCommand.userUid", target = "customerUid"),
            @Mapping(source = "modifyBookingCommand.bookingUid", target = "aggregateUid")
    })
    BookingEventStore toEventStore(ModifyBookingCommand modifyBookingCommand, int version);

    @Mappings({
            @Mapping(source = "eventStore.restaurant.id", target = "restaurantId")
    })
    BookingCreatedEvent toCreateEvent(BookingEventStore eventStore, UUID eventUid);

    BookingCancelledEvent toCancelEvent(BookingEventStore eventStore, UUID eventUid);

    BookingCompletedEvent toCompleteEvent(BookingEventStore eventStore, UUID eventUid);

    BookingModifiedEvent toModifyEvent(BookingEventStore eventStore, UUID eventUid);
}
