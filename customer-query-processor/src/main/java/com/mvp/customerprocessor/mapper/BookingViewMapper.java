package com.mvp.customerprocessor.mapper;

import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.dto.BookingViewDto;
import com.mvp.customerprocessor.entity.BookingView;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BookingViewMapper {

    @Mappings({
            @Mapping(source = "aggregateUid", target = "bookingUid"),
            @Mapping(source = "bookingStartTime", target = "bookingDate", qualifiedByName = "getBookingDate"),
            @Mapping(source = "bookingStartTime", target = "bookingDayOfWeek", qualifiedByName = "getDayOfTheWeek")
    })
    BookingView toView(BookingCreatedEvent event);

    BookingViewDto toDto(BookingView bookingView);

    @Named("getBookingDate")
    default LocalDate getDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate();
    }

    @Named("getDayOfTheWeek")
    default String getDayOfTheWeek(LocalDateTime dateTime) {
        LocalDate bookingDate = dateTime.toLocalDate();
        return bookingDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }
}
