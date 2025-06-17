package com.mvp.customerprocessor.event;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record RestaurantEventDto(
        Long id,
        String name,
        Integer maxCapacity,
        LocalTime workingStartTime,
        LocalTime workingEndTime
) implements Serializable {
}
