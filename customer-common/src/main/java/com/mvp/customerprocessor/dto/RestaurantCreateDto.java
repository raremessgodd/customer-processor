package com.mvp.customerprocessor.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;

@Builder
public record RestaurantCreateDto(
        @NotBlank @Size(max = 256) String name,
        @NotNull @Positive Integer maxCapacity,
        @NotNull LocalTime workingStartTime,
        @NotNull LocalTime workingEndTime
) implements Serializable {
    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeAfterStartTime() {
        return workingEndTime == null || workingStartTime == null || workingEndTime.isAfter(workingStartTime);
    }
}
