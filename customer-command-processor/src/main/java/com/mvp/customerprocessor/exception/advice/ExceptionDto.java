package com.mvp.customerprocessor.exception.advice;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ExceptionDto(
        Map<String, String> errors,
        LocalDateTime timestamp
) {
}
