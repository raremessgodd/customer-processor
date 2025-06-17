package com.mvp.customerprocessor.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record RestaurantDto(
        Long id,
        String name,
        Integer maxCapacity,
        LocalTime workingStartTime,
        LocalTime workingEndTime
) implements Serializable {

}
