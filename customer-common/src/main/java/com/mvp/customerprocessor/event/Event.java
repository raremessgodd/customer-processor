package com.mvp.customerprocessor.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@SuperBuilder
@NoArgsConstructor
public class Event {
    protected UUID eventUid;
    protected UUID aggregateUid;
    protected LocalDateTime createdAt = LocalDateTime.now();
    protected int version;
}
