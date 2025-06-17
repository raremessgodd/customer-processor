package com.mvp.customerprocessor.event_source.command;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@RequiredArgsConstructor
public class Command {
    protected UUID commandUid;
    protected UUID userUid;
}
