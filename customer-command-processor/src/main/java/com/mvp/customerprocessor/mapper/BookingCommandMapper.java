package com.mvp.customerprocessor.mapper;

import com.mvp.customerprocessor.dto.BookingCreateDto;
import com.mvp.customerprocessor.dto.BookingUpdateDto;
import com.mvp.customerprocessor.event_source.command.CancelBookingCommand;
import com.mvp.customerprocessor.event_source.command.CompleteBookingCommand;
import com.mvp.customerprocessor.event_source.command.CreateBookingCommand;
import com.mvp.customerprocessor.event_source.command.ModifyBookingCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookingCommandMapper {

    @Mapping(target = "commandUid", expression = "java(UUID.randomUUID())")
    CreateBookingCommand toCreateCommand(BookingCreateDto createDto, String restaurantName, UUID userUid);

    @Mapping(target = "commandUid", expression = "java(UUID.randomUUID())")
    CancelBookingCommand toCancelCommand(UUID bookingUid, UUID userUid);

    @Mapping(target = "commandUid", expression = "java(UUID.randomUUID())")
    CompleteBookingCommand toCompleteCommand(UUID bookingUid, UUID userUid);

    @Mapping(target = "commandUid", expression = "java(UUID.randomUUID())")
    ModifyBookingCommand toModifyCommand(BookingUpdateDto updateDto, UUID bookingUid, UUID userUid);
}
