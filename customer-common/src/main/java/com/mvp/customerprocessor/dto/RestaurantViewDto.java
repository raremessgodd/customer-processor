package com.mvp.customerprocessor.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record RestaurantViewDto(
        String id,
        Long restaurantId,
        String name,
        Integer maxCapacity,
        LocalTime workingStartTime,
        LocalTime workingEndTime
) implements Serializable {
}
